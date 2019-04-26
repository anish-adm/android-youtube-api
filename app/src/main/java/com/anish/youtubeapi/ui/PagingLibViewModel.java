package com.anish.youtubeapi.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.anish.youtubeapi.data.model.VideoEntity;
import com.anish.youtubeapi.datasource.VideoDataSourceClass;
import com.anish.youtubeapi.datasource.VideoDataSourceFactory;
import com.anish.youtubeapi.data.repository.VideoRepository;

import io.reactivex.disposables.CompositeDisposable;

public class PagingLibViewModel extends ViewModel {

    private VideoDataSourceFactory videoDataSourceFactory;
    private LiveData<PagedList<VideoEntity>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PagingLibViewModel(VideoRepository videoRepository) {
        videoDataSourceFactory = new VideoDataSourceFactory(videoRepository, compositeDisposable);
        initializePaging();
    }


    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20).build();

        listLiveData = new LivePagedListBuilder<>(videoDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(videoDataSourceFactory.getMutableLiveData(), VideoDataSourceClass::getProgressLiveStatus);

    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<VideoEntity>> getListLiveData() {
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}