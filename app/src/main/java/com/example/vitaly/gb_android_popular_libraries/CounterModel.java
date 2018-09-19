package com.example.vitaly.gb_android_popular_libraries;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CounterModel {

    private List<Integer> counters;

    CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Integer calculate(int index) {
        counters.set(index, counters.get(index) + 1);
        return counters.get(index);
    }

    public Observable<Integer> getCounter(int index) {
        switch (index) {
            case 0:
                return Observable.just(counters.get(index));
            case 1:
                return Observable.just(counters.get(index));
            case 2:
                return Observable.just(counters.get(index));
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }
}
