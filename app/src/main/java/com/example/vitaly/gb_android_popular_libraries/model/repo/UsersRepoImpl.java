package com.example.vitaly.gb_android_popular_libraries.model.repo;

import com.example.vitaly.gb_android_popular_libraries.model.api.ApiService;
import com.example.vitaly.gb_android_popular_libraries.model.cache.data.DataCache;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.util.NetworkStatus;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class UsersRepoImpl implements UsersRepo {

    private final DataCache cache;
    private final ApiService api;

    public UsersRepoImpl(DataCache cache, ApiService api) {
        this.cache = cache;
        this.api = api;
    }

    @Override
    public Single<User> getUser(String username) {
        if (NetworkStatus.isOnline()) {
            return api.getUser(username)
                    .subscribeOn(Schedulers.io())
                    .map(user -> {
                        cache.saveUser(user, username);
                        return user;
                    });
        } else {
            return cache.getUser(username);
        }
    }

    @Override
    public Single<List<Repository>> getUserRepos(User user) {
        if (NetworkStatus.isOnline()) {
            return api.getUserRepos(user.getReposUrl())
                    .subscribeOn(Schedulers.io())
                    .map(repos -> {
                        cache.saveRepos(repos, user);
                        return repos;
                    });
        } else {
            return cache.getRepos(user);
        }
    }
}
