package com.example.vitaly.gb_android_popular_libraries.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface MainView extends MvpView {

    void setButtonText(int id, Integer calculate);

    void setTextInTextView(String s);

    void pickImage();

    void showProgressDialog();

    void setImageOnView(String path);
}
