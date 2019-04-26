package com.anish.youtubeapi.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET(AppConstant.FetchVideoList)
    Observable<JsonElement> fetchVideoList(
            @Query("channelId") String channelId,
            @Query("pageToken") String pageToken);

}
