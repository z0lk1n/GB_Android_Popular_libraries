package com.example.vitaly.gb_android_popular_libraries.model.image.android;

import android.widget.ImageView;

import com.example.vitaly.gb_android_popular_libraries.model.image.IImageLoader;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements IImageLoader<ImageView> {
    @Override
    public void loadInto(String url, ImageView container) {
        Picasso.get().load(url).into(container);
    }
}
