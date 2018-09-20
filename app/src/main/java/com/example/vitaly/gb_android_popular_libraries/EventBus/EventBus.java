package com.example.vitaly.gb_android_popular_libraries.EventBus;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class EventBus<T> {

    private final Subject<T> subject;

    public EventBus(Observable<T> sourceFirst, Observable<T> sourceSecond) {
        this.subject = ReplaySubject.createWithSize(10);
        Observable.merge(sourceFirst, sourceSecond).subscribe(subject);
    }

    public void register(Observer<T> observer) {
        subject.subscribe(observer);
    }

    public void publish(Event<T> event) {
        subject.onNext(event.getPayload());
    }
}
