package com.example.vitaly.gb_android_popular_libraries.util;

import io.reactivex.Scheduler;

public interface SchedulersProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
