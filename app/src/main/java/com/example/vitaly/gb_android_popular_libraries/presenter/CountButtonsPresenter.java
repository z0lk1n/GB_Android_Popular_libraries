package com.example.vitaly.gb_android_popular_libraries.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.model.CounterModel;
import com.example.vitaly.gb_android_popular_libraries.ui.countbuttons.CountButtonsView;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProvider;

@InjectViewState
public final class CountButtonsPresenter extends MvpPresenter<CountButtonsView> {

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private CounterModel model;
    private SchedulersProvider schedulers;

    public CountButtonsPresenter(SchedulersProvider schedulers) {
        this.schedulers = schedulers;
        this.model = new CounterModel();
    }

    public void counterClick(int id) {
        switch (id) {
            case R.id.btn_one:
                model.getCounter(FIRST)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(integer -> model.calculate(FIRST))
                        .observeOn(schedulers.ui())
                        .subscribe(integer -> getViewState().setButtonText(id, integer));
                break;
            case R.id.btn_two:
                model.getCounter(SECOND)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(integer -> model.calculate(SECOND))
                        .observeOn(schedulers.ui())
                        .subscribe(integer -> getViewState().setButtonText(id, integer));
                break;
            case R.id.btn_three:
                model.getCounter(THIRD)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(integer -> model.calculate(THIRD))
                        .observeOn(schedulers.ui())
                        .subscribe(integer -> getViewState().setButtonText(id, integer));
                break;
        }
    }
}