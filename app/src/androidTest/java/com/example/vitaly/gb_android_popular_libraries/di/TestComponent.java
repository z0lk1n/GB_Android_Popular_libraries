package com.example.vitaly.gb_android_popular_libraries.di;

import com.example.vitaly.gb_android_popular_libraries.RealmDataCacheInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.UsersRepoInstrumentedTest;
import com.example.vitaly.gb_android_popular_libraries.di.modules.RepoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepoModule.class})
public interface TestComponent {
    void inject(UsersRepoInstrumentedTest test);

    void inject(RealmDataCacheInstrumentedTest test);
}
