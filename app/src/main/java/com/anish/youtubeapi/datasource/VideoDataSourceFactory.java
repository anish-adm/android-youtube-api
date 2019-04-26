package com.anish.youtubeapi.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.anish.youtubeapi.data.model.VideoEntity;
import com.anish.youtubeapi.data.repository.VideoRepository;

import io.reactivex.disposables.CompositeDisposable;

public class VideoDataSourceFactory extends DataSource.Factory<String, VideoEntity> {

    private MutableLiveData<VideoDataSourceClass> liveData;
    private VideoRepository videoRepository;
    private CompositeDisposable compositeDisposable;

    public VideoDataSourceFactory(VideoRepository videoRepository, CompositeDisposable compositeDisposable) {
        this.videoRepository = videoRepository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<VideoDataSourceClass> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<String, VideoEntity> create() {
        VideoDataSourceClass dataSourceClass = new VideoDataSourceClass(videoRepository, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
