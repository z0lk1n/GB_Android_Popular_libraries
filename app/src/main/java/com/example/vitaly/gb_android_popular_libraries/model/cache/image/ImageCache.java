package com.example.vitaly.gb_android_popular_libraries.model.cache.image;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Single;

public interface ImageCache {

    void saveImage(String url, Bitmap bitmap);

    Single<File> getImage(String url);
}
