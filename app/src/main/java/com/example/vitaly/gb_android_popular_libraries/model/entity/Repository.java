package com.example.vitaly.gb_android_popular_libraries.model.entity;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.Contract;

public final class Repository {

    @Expose private String id;
    @Expose private String name;

    public Repository(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Contract(pure = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
