package com.anish.youtubeapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppConstant {

    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/search/";
    public static final String YOUTUBE_BASE_URL = "http://youtube.com/";
    public static final String API_KEY = "AIzaSyC1ijtGYSSAzibl44UFTCIOOqT6BCIxRZo";
    public static final String FetchVideoList = "?part=snippet&order=date&maxResults=20";
    public static final String LOADING = "Loading";
    public static final String LOADED = "Loaded";
    public static final String CHECK_NETWORK_ERROR = "Check your network connection.";
    public static final String CHANNEL_ID = "UCVHFbqXqoYvEWM1Ddxl0QDg"; //CHANNEL ID FOR ANDROID DEVELOPER'S YOUTUBE ACCOUNT
    public static final String KEY_VIDEO_ID = "video_id";
    public static final long CONNECT_TIMEOUT = 30000;
    public static final long READ_TIMEOUT = 30000;
    public static final long WRITE_TIMEOUT = 30000;

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}