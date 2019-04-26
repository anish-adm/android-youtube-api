package com.anish.youtubeapi.data.repository;

import com.google.gson.JsonElement;
import com.anish.youtubeapi.utils.ApiCallInterface;
import com.anish.youtubeapi.utils.AppConstant;

import io.reactivex.Observable;

public class VideoRepository {

    private ApiCallInterface apiCallInterface;

    public VideoRepository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<JsonElement> executeVideoApi(String nextPageToken) {
        return apiCallInterface.fetchVideoList(AppConstant.CHANNEL_ID,nextPageToken);
    }

}
