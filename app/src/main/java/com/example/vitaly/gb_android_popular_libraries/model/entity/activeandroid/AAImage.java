package com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.jetbrains.annotations.Contract;

@Table(name = "images")
public class AAImage extends Model {

    @Column(name = "url")
    private String url;
    @Column(name = "image_path")
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
