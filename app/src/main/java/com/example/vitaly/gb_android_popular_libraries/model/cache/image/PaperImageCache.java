package com.example.vitaly.gb_android_popular_libraries.model.cache.image;

import android.graphics.Bitmap;

import com.example.vitaly.gb_android_popular_libraries.util.Hash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.Single;

public class PaperImageCache implements ImageCache {

    private final String cachePath;

    public PaperImageCache(String cachePath) {
        this.cachePath = cachePath;
    }

    @Override
    public void saveImage(String url, Bitmap bitmap) {
        try {
            File file = File.createTempFile("IMG_", ".png", new File(cachePath));

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            Paper.book("images").write(Hash.SHA1(url), file.toString());

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Single<File> getImage(String url) {
        String sha1 = Hash.SHA1(url);

        if (Paper.book("images").contains(sha1)) {
            String filePath = Paper.book("images").read(sha1);
            return Single.fromCallable(() -> new File(filePath));
        }
        return Single.error(new RuntimeException("No such image in cache"));
    }
}
