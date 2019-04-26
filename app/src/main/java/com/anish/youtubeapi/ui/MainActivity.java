package com.anish.youtubeapi.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.anish.youtubeapi.YouTubeAPIApp;
import com.anish.youtubeapi.R;
import com.anish.youtubeapi.data.model.VideoEntity;
import com.anish.youtubeapi.databinding.ActivityMainBinding;
import com.anish.youtubeapi.ui.adapter.VideoAdapter;
import com.anish.youtubeapi.ui.callback.VideoClickCallback;
import com.anish.youtubeapi.utils.AppConstant;
import com.anish.youtubeapi.utils.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Activity thisActivity;

    private SwipeRefreshLayout swipeContainer;

    @Inject
    ViewModelFactory viewModelFactory;

    PagingLibViewModel viewModel;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });


        ((YouTubeAPIApp) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PagingLibViewModel.class);
        thisActivity = this;
        init();
    }

    private void init() {

        binding.list.setLayoutManager(new LinearLayoutManager(this));
        VideoAdapter adapter = new VideoAdapter(videoClickCallback);
        binding.list.setAdapter(adapter);

        if (!AppConstant.checkInternetConnection(this)) {
            Snackbar.make(findViewById(android.R.id.content), AppConstant.CHECK_NETWORK_ERROR, Snackbar.LENGTH_SHORT)
                    .show();
        }

        viewModel.getListLiveData().observe(this, adapter::submitList);

        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(AppConstant.LOADING)) {
                binding.progress.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(AppConstant.LOADED)) {
                binding.progress.setVisibility(View.GONE);
            }
            swipeContainer.setRefreshing(false);
        });

    }

    private final VideoClickCallback videoClickCallback = new VideoClickCallback() {
        @Override
        public void onClick(VideoEntity videoEntity) {
            Intent intent = new Intent(thisActivity, VideoActivity.class);
            intent.putExtra(AppConstant.KEY_VIDEO_ID, videoEntity.getVideoId());
            thisActivity.startActivity(intent);
        }
    };
}
