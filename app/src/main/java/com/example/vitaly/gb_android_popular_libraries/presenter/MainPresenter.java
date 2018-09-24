package com.example.vitaly.gb_android_popular_libraries.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.model.CounterModel;
import com.example.vitaly.gb_android_popular_libraries.ui.MainView;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProvider;

import java.io.IOException;

import io.reactivex.Single;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private CounterModel model;
    private SchedulersProvider schedulers;
    private FileConverterManager converter;

    public MainPresenter(SchedulersProvider schedulers, FileConverterManager converter) {
        this.schedulers = schedulers;
        this.converter = converter;
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
    public void sendByteArrayFromRequest(byte[] byteArray) {
        Single.just(byteArray)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.computation())
                .subscribe(this::createImageFileFromByteArray);
    }

    private void createImageFileFromByteArray(byte[] byteArr) {
        String suffix = ".png";
        try {
            byte[] convertByteArr = converter.convertToPNG(byteArr);
            String filePath = converter.createImageFile(suffix, convertByteArr);
            showImage(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    private void showImage(String path) {
        Single.just(path)
                .observeOn(schedulers.ui())
                .subscribe(s -> getViewState().setImageOnView(s));
    }
}
