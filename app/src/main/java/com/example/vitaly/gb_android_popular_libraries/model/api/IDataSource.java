package com.example.vitaly.gb_android_popular_libraries.model.api;

import com.example.vitaly.gb_android_popular_libraries.model.entity.Repos;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface IDataSource {
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String username);

    @GET
    Observable<List<Repos>> getUserRepos(@Url String username);
}