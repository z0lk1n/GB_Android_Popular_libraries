package com.example.vitaly.gb_android_popular_libraries.EventBus;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class EventBusImpl<T> implements EventBus {

    private final Subject<T> subject;

    public EventBusImpl(Observable<T> sourceFirst, Observable<T> sourceSecond) {
        this.subject = ReplaySubject.createWithSize(10);
        Observable.merge(sourceFirst, sourceSecond).subscribe(subject);
    }

    @Override
    public void register(Observer observer) {
        subject.subscribe(observer);
    }

    @Override
    public void publish(Event event) {
        subject.onNext((T) event.getPayload());
    }
}
