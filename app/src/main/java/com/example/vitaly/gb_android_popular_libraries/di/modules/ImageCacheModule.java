package com.example.vitaly.gb_android_popular_libraries.di.modules;

import android.os.Environment;

import com.example.vitaly.gb_android_popular_libraries.App;
import com.example.vitaly.gb_android_popular_libraries.model.cache.image.AAImageCache;
import com.example.vitaly.gb_android_popular_libraries.model.cache.image.ImageCache;
import com.example.vitaly.gb_android_popular_libraries.model.cache.image.PaperImageCache;
import com.example.vitaly.gb_android_popular_libraries.model.cache.image.RealmImageCache;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageCacheModule {

    @Named("cachePath")
    @Provides
    public String getCachePath(App app) {
        File file = app.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file == null) {
            return "";
        }
        return file.toString();
    }

    @Named("realmCache")
    @Provides
    public ImageCache cacheRealm(@Named("cachePath") String cachePath) {
        return new RealmImageCache(cachePath);
    }

    @Named("aaCache")
    @Provides
    public ImageCache cacheAA(@Named("cachePath") String cachePath) {
        return new AAImageCache(cachePath);
    }

    @Named("paperCache")
    @Provides
    public ImageCache cachePaper(@Named("cachePath") String cachePath) {
        return new PaperImageCache(cachePath);
    }
}
