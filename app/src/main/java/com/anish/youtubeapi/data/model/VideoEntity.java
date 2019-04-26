package com.anish.youtubeapi.data.model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class VideoEntity {

    private String thumbnailURL;
    private String title;
    private String videoId;

    public VideoEntity(String title, String thumbnailURL,String videoId) {
        this.thumbnailURL = thumbnailURL;
        this.title = title;
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getTitle() {
        return title;
    }


    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public static DiffUtil.ItemCallback<VideoEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<VideoEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull VideoEntity oldItem, @NonNull VideoEntity newItem) {
            return oldItem.title.equals(newItem.title);
        }

        @Override
        public boolean areContentsTheSame(@NonNull VideoEntity oldItem, @NonNull VideoEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        VideoEntity article = (VideoEntity) obj;
        return article.title.equals(this.title);
    }

}
