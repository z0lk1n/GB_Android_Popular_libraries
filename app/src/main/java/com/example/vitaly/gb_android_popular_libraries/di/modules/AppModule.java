package com.example.vitaly.gb_android_popular_libraries.di.modules;

import com.example.vitaly.gb_android_popular_libraries.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public App app() {
        return app;
    }
}

