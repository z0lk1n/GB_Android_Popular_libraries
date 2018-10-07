package com.example.vitaly.gb_android_popular_libraries.di.modules;

import android.widget.ImageView;

import com.example.vitaly.gb_android_popular_libraries.model.cache.image.ImageCache;
import com.example.vitaly.gb_android_popular_libraries.model.image.ImageLoader;
import com.example.vitaly.gb_android_popular_libraries.model.image.glide.ImageLoaderAndToCacheGlide;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = ImageCacheModule.class)
public class ImageLoaderModule {

    @Provides
    public ImageLoader<ImageView> imageLoader(@Named("realmCache") ImageCache cache) {
        return new ImageLoaderAndToCacheGlide(cache);
    }
}