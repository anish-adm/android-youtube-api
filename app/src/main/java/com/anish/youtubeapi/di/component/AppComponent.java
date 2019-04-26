package com.anish.youtubeapi.di.component;

import com.anish.youtubeapi.di.module.AppModule;
import com.anish.youtubeapi.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void doInjection(MainActivity mainActivity);
}
