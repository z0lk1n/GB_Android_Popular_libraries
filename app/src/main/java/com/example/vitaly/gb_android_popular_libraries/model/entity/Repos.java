package com.example.vitaly.gb_android_popular_libraries.model.entity;

import com.google.gson.annotations.Expose;

public final class Repos {

    @Expose private final String name;

    public Repos(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
