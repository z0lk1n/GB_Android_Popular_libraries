package com.example.vitaly.gb_android_popular_libraries.di;

import com.example.vitaly.gb_android_popular_libraries.AADataCacheInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.PaperDataCacheInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.RealmDataCacheInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.RealmImageCacheInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.UsersRepoInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.di.modules.AppModule;
import com.example.vitaly.gb_android_popular_libraries.di.modules.ImageCacheModule;
import com.example.vitaly.gb_android_popular_libraries.di.modules.RepoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepoModule.class, ImageCacheModule.class, AppModule.class})
public interface TestComponent {
    void inject(UsersRepoInstrumentedTest test);

    void inject(RealmDataCacheInstrumentedTest test);

    void inject(PaperDataCacheInstrumentedTest test);

    void inject(AADataCacheInstrumentedTest test);

    void inject(RealmImageCacheInstrumentedTest test);
}
