package com.example.vitaly.gb_android_popular_libraries.model.cache.data;

import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;

import java.util.List;

import io.reactivex.Single;

public interface DataCache {

    void saveUser(User user, String username);

    Single<User> getUser(String username);

    void saveRepos(List<Repository> repos, User user);

    Single<List<Repository>> getRepos(User user);
}
