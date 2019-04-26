package com.anish.youtubeapi.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.anish.youtubeapi.data.repository.VideoRepository;
import com.anish.youtubeapi.ui.PagingLibViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private VideoRepository videoRepository;

    @Inject
    public ViewModelFactory(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PagingLibViewModel.class)) {
            return (T) new PagingLibViewModel(videoRepository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
