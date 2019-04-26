package com.anish.youtubeapi.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.anish.youtubeapi.data.repository.VideoRepository;
import com.anish.youtubeapi.utils.ApiCallInterface;
import com.anish.youtubeapi.utils.AppConstant;
import com.anish.youtubeapi.utils.RequestInterceptor;
import com.anish.youtubeapi.utils.ViewModelFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Module
public class AppModule {

    @Provides
    @Singleton
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(AppConstant.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(AppConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(AppConstant.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    VideoRepository getRepository(ApiCallInterface apiCallInterface) {
        return new VideoRepository(apiCallInterface);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(VideoRepository myVideoRepository) {
        return new ViewModelFactory(myVideoRepository);
    }
}
