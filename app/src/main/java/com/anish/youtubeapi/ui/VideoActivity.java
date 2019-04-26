package com.anish.youtubeapi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.anish.youtubeapi.utils.AppConstant;
import com.google.android.exoplayer2.ui.PlayerView;
import com.anish.youtubeapi.R;
import com.anish.youtubeapi.media.ExoMediaManager;

import java.util.Arrays;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class VideoActivity extends AppCompatActivity {
    private String mYoutubeLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Bundle bundle = getIntent().getExtras();
        String videoID = bundle.getString(AppConstant.KEY_VIDEO_ID);
        mYoutubeLink = AppConstant.YOUTUBE_BASE_URL+"watch?v="+videoID;
        extractYoutubeUrl();
    }

    private void extractYoutubeUrl() {


        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    List<Integer> iTags = Arrays.asList(22, 137, 18);
                    for (Integer iTag : iTags) {
                        YtFile ytFile = ytFiles.get(iTag);
                        if (ytFile != null) {
                            String downloadUrl = ytFile.getUrl();
                            if (downloadUrl != null && !downloadUrl.isEmpty()) {
                                playVideo(downloadUrl);
                                return;
                            }
                        }
                    }
                }
            }
        }.extract(mYoutubeLink, true, true);
    }

    private void playVideo(String downloadUrl) {
        PlayerView mPlayerView = findViewById(R.id.mPlayerView);
        mPlayerView.setPlayer(ExoMediaManager.getSharedInstance(VideoActivity.this).getPlayerView().getPlayer());
        ExoMediaManager.getSharedInstance(VideoActivity.this).playStream(downloadUrl);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ExoMediaManager.getSharedInstance(VideoActivity.this).destroyPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(ExoMediaManager.getSharedInstance(VideoActivity.this).isPlayerPlaying()){
            ExoMediaManager.getSharedInstance(VideoActivity.this).stopPlayer(true);
        }
    }
}
