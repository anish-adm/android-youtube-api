package com.anish.youtubeapi.datasource;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.anish.youtubeapi.data.model.VideoEntity;
import com.anish.youtubeapi.data.repository.VideoRepository;
import com.anish.youtubeapi.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class VideoDataSourceClass extends PageKeyedDataSource<String, VideoEntity> {

    private VideoRepository videoRepository;
    private Gson gson;
    private String nextPageToken = "";
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;

    VideoDataSourceClass(VideoRepository videoRepository, CompositeDisposable compositeDisposable) {
        this.videoRepository = videoRepository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = builder.setLenient().create();
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, VideoEntity> callback) {

        videoRepository.executeVideoApi(nextPageToken)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(AppConstant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(AppConstant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("items");

                            ArrayList<VideoEntity> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject videoInfo = array.getJSONObject(i).getJSONObject("snippet");
                                JSONObject videoID = array.getJSONObject(i).getJSONObject("id");
                                arrayList.add(new VideoEntity(videoInfo.optString("title"),
                                        videoInfo.getJSONObject("thumbnails").getJSONObject("medium").optString("url"),
                                        videoID.optString("videoId")));
                            }

                            nextPageToken = object.optString("nextPageToken");
                            callback.onResult(arrayList, null, nextPageToken);
                        },
                        throwable ->
                                progressLiveStatus.postValue(AppConstant.LOADED)

                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, VideoEntity> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, VideoEntity> callback) {

        videoRepository.executeVideoApi(params.key)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(AppConstant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(AppConstant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("items");

                            ArrayList<VideoEntity> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject videoInfo = array.getJSONObject(i).getJSONObject("snippet");
                                JSONObject videoID = array.getJSONObject(i).getJSONObject("id");
                                arrayList.add(new VideoEntity(videoInfo.optString("title"),
                                        videoInfo.getJSONObject("thumbnails").getJSONObject("medium").optString("url"),
                                        videoID.optString("videoId")));
                            }
                            String nextPageToken = object.optString("nextPageToken");
                            callback.onResult(arrayList, nextPageToken);

                        },
                        throwable ->
                                progressLiveStatus.postValue(AppConstant.LOADED)
                );
    }
}
