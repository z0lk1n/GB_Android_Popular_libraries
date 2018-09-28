package com.example.vitaly.gb_android_popular_libraries.model.image;

public interface IImageLoader<T> {
    void loadInto(String url, T container);
}
