package com.example.vitaly.gb_android_popular_libraries.model.cache.image;

import android.graphics.Bitmap;

import com.example.vitaly.gb_android_popular_libraries.model.entity.realm.RealmImage;
import com.example.vitaly.gb_android_popular_libraries.util.Hash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Single;
import io.realm.Realm;

public class RealmImageCache implements ImageCache {

    private final String cachePath;

    public RealmImageCache(String cachePath) {
        this.cachePath = cachePath;
    }

    @Override
    public void saveImage(String url, Bitmap bitmap) {
        String sha1 = Hash.SHA1(url);
        Realm realm = Realm.getDefaultInstance();
        RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", sha1).findFirst();

        try {
            File file = File.createTempFile("IMG", ".png", new File(cachePath));
            String imagePath = file.toString();

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            if (realmImage == null) {
                realm.executeTransaction(innerRealm -> {
                    RealmImage newRealmImage = innerRealm.createObject(RealmImage.class, sha1);
                    newRealmImage.setImagePath(imagePath);
                });
            } else {
                realm.executeTransaction(innerRealm -> realmImage.setImagePath(imagePath));
            }
            realm.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Single<File> getImage(String url) {
        return Single.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmImage realmImage = realm.where(RealmImage.class)
                    .equalTo("url", Hash.SHA1(url))
                    .findFirst();

            if (realmImage == null) {
                emitter.onError(new RuntimeException("No such image in cache"));
            } else {
                emitter.onSuccess(new File(realmImage.getImagePath()));
            }
            realm.close();
        });
    }
}
