package com.example.vitaly.gb_android_popular_libraries.EventBus;


import io.reactivex.Observer;

public interface EventBus<T> {

    void register(Observer<T> observer);

    void publish(Event<T> event);
}
