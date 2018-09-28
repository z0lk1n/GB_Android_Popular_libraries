package com.example.vitaly.gb_android_popular_libraries.ui.convertimage;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface ConvertImageView extends MvpView {

    void pickImage();

    void showProgressDialog();

    void setImageOnView(String path);
}
