package com.example.vitaly.gb_android_popular_libraries.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.ui.convertimage.ConvertImageView;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProvider;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

@InjectViewState
public final class ConvertImagePresenter extends MvpPresenter<ConvertImageView> {

    private SchedulersProvider schedulers;
    private Disposable disposable;

    @Inject FileConverterManager converter;

    public ConvertImagePresenter(SchedulersProvider schedulers) {
        this.schedulers = schedulers;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        showImageList();
    }

    public void chooseImageClick() {
        getViewState().pickImage();
    }

    public void sendByteArrayFromRequest(byte[] byteArr) {
        try {
            converter.convertToPNG(byteArr)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                            getViewState().showProgressDialog();
                        }

                        @Override
                        public void onComplete() {
                            getViewState().closeProgressDialog();
                            showImageList();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getViewState().closeProgressDialog();
                            showImageList();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    private void showImageList() {
        converter.getImageListFromDir()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(s -> getViewState().setImageListOnView(s));
    }

    public void cancelFileConversion() {
        getViewState().closeProgressDialog();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
