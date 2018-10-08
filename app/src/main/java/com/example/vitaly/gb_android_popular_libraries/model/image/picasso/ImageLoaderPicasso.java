package com.example.vitaly.gb_android_popular_libraries.model.image.picasso;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.vitaly.gb_android_popular_libraries.model.image.ImageLoader;
import com.squareup.picasso.Picasso;

public final class ImageLoaderPicasso implements ImageLoader<ImageView> {

    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        Picasso.get().load(url).into(container);
    }
}
