package com.example.vitaly.gb_android_popular_libraries.di.modules;

import com.example.vitaly.gb_android_popular_libraries.model.api.ApiService;
import com.example.vitaly.gb_android_popular_libraries.model.cache.data.DataCache;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepoImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class, DataCacheModule.class})
public class RepoModule {

    @Provides
    public UsersRepo repo(@Named("realmCache") DataCache cache, ApiService api) {
        return new UsersRepoImpl(cache, api);
    }
}
