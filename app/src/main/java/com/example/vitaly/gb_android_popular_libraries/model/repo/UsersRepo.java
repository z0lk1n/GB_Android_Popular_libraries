package com.example.vitaly.gb_android_popular_libraries.model.repo;

import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;

import java.util.List;

import io.reactivex.Single;

public interface UsersRepo {

    Single<User> getUser(String username);

    Single<List<Repository>> getUserRepos(User user);
}