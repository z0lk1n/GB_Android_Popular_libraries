package com.example.vitaly.gb_android_popular_libraries.model.image.glide;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.vitaly.gb_android_popular_libraries.model.cache.image.ImageCache;
import com.example.vitaly.gb_android_popular_libraries.model.image.ImageLoader;
import com.example.vitaly.gb_android_popular_libraries.util.NetworkStatus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class ImageLoaderAndToCacheGlide implements ImageLoader<ImageView> {

    private final ImageCache cache;

    public ImageLoaderAndToCacheGlide(ImageCache cache) {
        this.cache = cache;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        if (NetworkStatus.isOffline()) {
            cache.getImage(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(file ->
                            GlideApp.with(container.getContext())
                                    .load(file)
                                    .into(container));
        } else {
            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    cache.saveImage(url, resource);
                    return false;
                }
            }).into(container);
        }
    }
}

