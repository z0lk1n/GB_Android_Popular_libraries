package com.example.vitaly.gb_android_popular_libraries.util;

import java.io.IOException;
import java.io.InputStream;

public interface FileConverterManager {

    byte[] getByteArrayFromStream(InputStream inputStream) throws IOException;

    byte[] convertToPNG(byte[] byteArr) throws IOException;

    String createImageFile(String suffix, byte[] byteArr) throws IOException;
}
