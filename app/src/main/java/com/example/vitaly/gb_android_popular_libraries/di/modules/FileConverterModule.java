package com.example.vitaly.gb_android_popular_libraries.di.modules;

import android.os.Environment;

import com.example.vitaly.gb_android_popular_libraries.App;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManagerImpl;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FileConverterModule {

    @Named("storageDir")
    @Provides
    public File getStorageDir(App app) {
        return app.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Provides
    public FileConverterManager fileConverter(@Named("storageDir") File storageDir) {
        return new FileConverterManagerImpl(storageDir);
    }
}
