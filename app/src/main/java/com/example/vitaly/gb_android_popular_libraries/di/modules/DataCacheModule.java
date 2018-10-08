package com.example.vitaly.gb_android_popular_libraries.di.modules;

import com.example.vitaly.gb_android_popular_libraries.model.cache.data.AADataCache;
import com.example.vitaly.gb_android_popular_libraries.model.cache.data.DataCache;
import com.example.vitaly.gb_android_popular_libraries.model.cache.data.PaperDataCache;
import com.example.vitaly.gb_android_popular_libraries.model.cache.data.RealmDataCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DataCacheModule {

    @Named("realmCache")
    @Provides
    public DataCache cacheRealm() {
        return new RealmDataCache();
    }

    @Named("aaCache")
    @Provides
    public DataCache cacheAA() {
        return new AADataCache();
    }

    @Named("paperCache")
    @Provides
    public DataCache cachePaper() {
        return new PaperDataCache();
    }
}
