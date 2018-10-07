package com.example.vitaly.gb_android_popular_libraries.model.entity;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public final class User {

    @Expose private String login;
    @Expose private String avatarUrl;
    @Expose private String reposUrl;

    public User(String login, String avatarUrl, String reposUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    @Expose private List<Repository> repos = new ArrayList<>();

    @Contract(pure = true)
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Contract(pure = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Contract(pure = true)
    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    @Contract(pure = true)
    public List<Repository> getRepos() {
        return repos;
    }

    public void setRepos(List<Repository> repos) {
        this.repos = repos;
    }
}
