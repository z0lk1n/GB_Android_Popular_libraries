package com.example.vitaly.gb_android_popular_libraries.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class FileConverterManagerImpl implements FileConverterManager {

    private final File storageDir;

    public FileConverterManagerImpl(File storageDir) {
        this.storageDir = storageDir;
    }

    @Override
    @SuppressLint("CheckResult")
    public byte[] getByteArrayFromStream(final InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        Completable.fromAction(() -> {
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len;
            while (inputStream.available() > 0 && (len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        }).subscribeOn(Schedulers.io());

        return byteBuffer.toByteArray();
    }

    @Override
    public Completable convertToPNG(final byte[] byteArr) {
        return Completable.create(emitter -> {
            try {
                Thread.sleep(3000);
                createPngFileFromByteArray(byteArr);
                emitter.onComplete();
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            }
        });
    }

    private void createPngFileFromByteArray(byte[] byteArr) throws IOException {
        String prefix = "IMG_";
        String suffix = ".png";

        FileOutputStream out = new FileOutputStream(File.createTempFile(prefix, suffix, storageDir));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length, options);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        bitmap.recycle();
        out.close();
    }

    @NonNull
    @Override
    public Single<String> getImageListFromDir() {
        return Single.fromCallable(() -> {
            StringBuilder sb = new StringBuilder();
            for (File file : storageDir.listFiles()) {
                sb.append(file.getName()).append("\n");
            }
            return sb.toString();
        });
    }
}
