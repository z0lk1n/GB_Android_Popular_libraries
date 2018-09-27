package com.example.vitaly.gb_android_popular_libraries.model.repo;

import com.example.vitaly.gb_android_popular_libraries.model.api.ApiHolder;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repos;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UsersRepo {
    private User user;
    private List<Repos> reposList;

    public Observable<User> getUser(String username) {
        return ApiHolder
                .getApi()
                .getUser(username)
                .subscribeOn(Schedulers.io())
                .map(user -> this.user = user);
    }

    public Observable<List<Repos>> getUserRepos() {
        return ApiHolder
                .getApi()
                .getUserRepos(user.getReposUrl())
                .subscribeOn(Schedulers.io())
                .switchMap(repos -> {
                    UsersRepo.this.reposList = repos;
                    return Observable.just(reposList);
                });
    }
}
