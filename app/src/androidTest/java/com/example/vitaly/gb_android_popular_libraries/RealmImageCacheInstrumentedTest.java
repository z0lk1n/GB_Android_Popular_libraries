package com.example.vitaly.gb_android_popular_libraries;

import android.graphics.Bitmap;

import com.example.vitaly.gb_android_popular_libraries.di.TestComponent;
import com.example.vitaly.gb_android_popular_libraries.model.cache.image.ImageCache;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.TestObserver;

public class RealmImageCacheInstrumentedTest {

    @Inject @Named("realmCache") ImageCache cache;

    @BeforeClass
    public static void setupClass() {


    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent.builder().build();
        component.inject(this);
    }

    @After
    public void after() {

    }

    @Test
    public void imageTest() {
        String url = "someUrl";
        Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.RGB_565);

        cache.saveImage(url, bitmap);

        TestObserver<File> observer = new TestObserver<>();
        cache.getImage(url).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        observer.assertComplete();
    }
}
