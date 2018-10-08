package com.example.vitaly.gb_android_popular_libraries.model;

import org.jetbrains.annotations.Contract;

public final class Event {

    private final String message;

    public Event(String message) {
        this.message = message;
    }

    @Contract(pure = true)
    public String getMessage() {
        return message;
    }
}
