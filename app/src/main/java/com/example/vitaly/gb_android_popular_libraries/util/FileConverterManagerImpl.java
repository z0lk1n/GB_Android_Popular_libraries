package com.example.vitaly.gb_android_popular_libraries.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileConverterManagerImpl implements FileConverterManager {

    private File storageDir;

    public FileConverterManagerImpl(File storageDir) {
        this.storageDir = storageDir;
    }

    @Override
    public byte[] getByteArrayFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while (inputStream.available() > 0 && (len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    @Override
    public byte[] convertToPNG(byte[] rawByteArr) throws IOException {
        ByteArrayOutputStream convertStream = new ByteArrayOutputStream();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawByteArr, 0, rawByteArr.length, options);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, convertStream);

        byte[] convertByteArray = convertStream.toByteArray();

        convertStream.close();
        bitmap.recycle();

        return convertByteArray;
    }

    @NonNull
    @Override
    public String createImageFile(String suffix, byte[] byteArr) throws IOException {
        File file = File.createTempFile(getFileName(), suffix, storageDir);
        FileOutputStream out = new FileOutputStream(file);
        out.write(byteArr);
        out.flush();
        out.close();
        return file.getAbsolutePath();
    }

    private String getFileName()   {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
    }
}
