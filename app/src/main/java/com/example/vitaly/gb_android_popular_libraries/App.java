package com.example.vitaly.gb_android_popular_libraries;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.vitaly.gb_android_popular_libraries.di.AppComponent;
import com.example.vitaly.gb_android_popular_libraries.di.DaggerAppComponent;
import com.example.vitaly.gb_android_popular_libraries.di.modules.AppModule;
import com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid.AAImage;
import com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid.AARepository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid.AAUser;

import org.jetbrains.annotations.Contract;

import io.paperdb.Paper;
import io.realm.Realm;
import timber.log.Timber;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Timber.plant(new Timber.DebugTree());

        Paper.init(this);
        Realm.init(this);

        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("MyDb.db")
                .addModelClass(AAUser.class)
                .addModelClass(AARepository.class)
                .addModelClass(AAImage.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);
    }

    @Contract(pure = true)
    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
