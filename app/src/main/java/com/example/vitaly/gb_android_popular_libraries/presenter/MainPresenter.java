package com.example.vitaly.gb_android_popular_libraries.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.model.CounterModel;
import com.example.vitaly.gb_android_popular_libraries.ui.MainView;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private CounterModel model;
    private SchedulersProvider schedulers;

    public MainPresenter(SchedulersProvider schedulers) {
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

    public void textChanged(String s) {
        getViewState().setTextInTextView(s);
    }

    public void chooseImageClick() {
        getViewState().pickImage();
    }

    @SuppressLint("CheckResult")
    public void sendByteArray(byte[] byteArray) {
        Single.just(byteArray)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.computation())
                .subscribe(this::createImageFileFromByteArray);
    }

    private void createImageFileFromByteArray(byte[] byteArray) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String suffix = ".png";
        getViewState().createImageFile(timeStamp, suffix, byteArray);
    }

    @SuppressLint("CheckResult")
    public void sendFilePath(String path) {
        Single.just(path)
                .observeOn(schedulers.ui())
                .subscribe(s -> getViewState().setImageOnView(s));
    }
}
