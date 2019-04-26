package com.anish.youtubeapi.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anish.youtubeapi.R;
import com.anish.youtubeapi.data.model.VideoEntity;
import com.anish.youtubeapi.databinding.VideoListItemBinding;
import com.anish.youtubeapi.ui.callback.VideoClickCallback;

public class VideoAdapter extends PagedListAdapter<VideoEntity, VideoAdapter.VideoViewHolder> {

    public VideoAdapter(@Nullable VideoClickCallback videoClickCallback) {
        super(VideoEntity.DIFF_CALLBACK);
        this.videoClickCallback = videoClickCallback;
    }

    @Nullable
    private final VideoClickCallback videoClickCallback;

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        VideoListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.video_list_item, parent, false);

        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoViewHolder holder, int position) {
        holder.binding.setVideo(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoListItemBinding binding;

        VideoViewHolder(VideoListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.setCallback(videoClickCallback);
        }

    }
}
