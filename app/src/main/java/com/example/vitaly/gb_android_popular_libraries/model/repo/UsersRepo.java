package com.example.vitaly.gb_android_popular_libraries.model.repo;

import com.example.vitaly.gb_android_popular_libraries.model.api.ApiHolder;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repos;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class UsersRepo {

    private User user;

    public Single<User> getUser(String username) {
        return ApiHolder
                .getApi()
                .getUser(username)
                .subscribeOn(Schedulers.io())
                .map(user -> this.user = user);
    }

    public Single<List<Repos>> getUserRepos() {
        return ApiHolder.getApi().getUserRepos(user.getReposUrl());
    }
}
