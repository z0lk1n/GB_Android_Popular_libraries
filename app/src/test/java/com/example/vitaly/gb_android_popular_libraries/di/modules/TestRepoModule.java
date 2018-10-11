package com.example.vitaly.gb_android_popular_libraries.di.modules;

import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestRepoModule {

    @Provides
    public UsersRepo usersRepo() {
        return Mockito.mock(UsersRepo.class);
    }
}