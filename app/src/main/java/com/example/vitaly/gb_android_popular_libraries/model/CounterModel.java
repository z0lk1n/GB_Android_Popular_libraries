package com.example.vitaly.gb_android_popular_libraries.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public final class CounterModel {

    private final List<Integer> counters;

    public CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Integer calculate(final int index) {
        counters.set(index, counters.get(index) + 1);
        return counters.get(index);
    }

    public Single<Integer> getCounter(final int index) {
        return Single.create(e -> e.onSuccess(counters.get(index)));
    }
}
