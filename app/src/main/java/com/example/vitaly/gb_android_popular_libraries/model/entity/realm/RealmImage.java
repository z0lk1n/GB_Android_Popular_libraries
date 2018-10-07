package com.example.vitaly.gb_android_popular_libraries.model.entity.realm;

import org.jetbrains.annotations.Contract;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmImage extends RealmObject {

    @PrimaryKey
    private String url;
    private String imagePath;

    @Contract(pure = true)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Contract(pure = true)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
