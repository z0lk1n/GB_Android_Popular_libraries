package com.example.vitaly.gb_android_popular_libraries.model.cache.image;

import android.graphics.Bitmap;

import com.activeandroid.query.Select;
import com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid.AAImage;
import com.example.vitaly.gb_android_popular_libraries.util.Hash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Single;

public class AAImageCache implements ImageCache {

    private final String cachePath;

    public AAImageCache(String cachePath) {
        this.cachePath = cachePath;
    }

    @Override
    public void saveImage(String url, Bitmap bitmap) {
        try {
            String sha1 = Hash.SHA1(url);

            File file = File.createTempFile("IMG_", ".png", new File(cachePath));

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            AAImage aaImage = new Select()
                    .from(AAImage.class)
                    .where("url = ?", sha1)
                    .executeSingle();
            if (aaImage == null) {
                aaImage = new AAImage();
                aaImage.setUrl(sha1);
            }
            aaImage.setImagePath(file.toString());
            aaImage.save();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Single<File> getImage(String url) {
        String sha1 = Hash.SHA1(url);

        AAImage aaImage = new Select()
                .from(AAImage.class)
                .where("url = ?", sha1)
                .executeSingle();

        if (aaImage == null) {
            return Single.error(new RuntimeException("No such image in cache"));
        }
        return Single.fromCallable(() -> new File(aaImage.getImagePath()));
    }
}
