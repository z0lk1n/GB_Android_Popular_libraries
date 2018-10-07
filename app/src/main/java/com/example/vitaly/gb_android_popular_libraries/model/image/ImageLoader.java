package com.example.vitaly.gb_android_popular_libraries.model.image;

import android.support.annotation.Nullable;

public interface ImageLoader<T> {

    void loadInto(@Nullable String url, T container);
}
