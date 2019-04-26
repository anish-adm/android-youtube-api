package com.anish.youtubeapi;

import android.app.Application;
import android.content.Context;

import com.anish.youtubeapi.di.component.AppComponent;
import com.anish.youtubeapi.di.component.DaggerAppComponent;
import com.anish.youtubeapi.di.module.AppModule;

public class YouTubeAPIApp extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
