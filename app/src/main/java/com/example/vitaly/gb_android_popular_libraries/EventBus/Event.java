package com.example.vitaly.gb_android_popular_libraries.EventBus;

public class Event<T> {

    private final T payload;

    public Event(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
