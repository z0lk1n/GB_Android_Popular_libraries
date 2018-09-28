package com.example.vitaly.gb_android_popular_libraries.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.ui.convertimage.ConvertImageView;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProvider;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
public class ConvertImagePresenter extends MvpPresenter<ConvertImageView> {

    private SchedulersProvider schedulers;
    private FileConverterManager converter;
    private Disposable disposable;

    public ConvertImagePresenter(SchedulersProvider schedulers, FileConverterManager converter) {
        this.schedulers = schedulers;
        this.converter = converter;
    }

    public void chooseImageClick() {
        getViewState().pickImage();
    }

    @SuppressLint("CheckResult")
    public void sendByteArrayFromRequest(byte[] byteArray) {
        Single<byte[]> single = Single.just(byteArray)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.computation());

        disposable = single.subscribeWith(new DisposableSingleObserver<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                showProgressDialog();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createImageFileFromByteArray(byteArray);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
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

    @SuppressLint("CheckResult")
    private void showProgressDialog() {
        Completable.fromAction(() -> getViewState().showProgressDialog())
                .subscribeOn(schedulers.ui());
    }

    public void cancelFileConversion() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
