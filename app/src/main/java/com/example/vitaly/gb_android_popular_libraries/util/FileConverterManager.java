package com.example.vitaly.gb_android_popular_libraries.util;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FileConverterManager {

    byte[] getByteArrayFromStream(InputStream inputStream) throws IOException;

    Completable convertToPNG(byte[] byteArr) throws IOException;

    Single<String> getImageListFromDir();
}
