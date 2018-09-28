package com.example.vitaly.gb_android_popular_libraries.model.entity;

import com.google.gson.annotations.Expose;

public final class User {

    @Expose private final String login;
    @Expose private final String avatarUrl;
    @Expose private final String reposUrl;

    public User(String login, String avatarUrl, String reposUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }
}
