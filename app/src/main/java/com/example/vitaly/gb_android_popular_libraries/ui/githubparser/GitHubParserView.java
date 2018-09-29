package com.example.vitaly.gb_android_popular_libraries.ui.githubparser;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface GitHubParserView extends MvpView {

    void setUsernameText(String username);

    void loadImage(String url);

    void updateList();

    void showChooseUserDialog();

    void showNotifyingMessage(String msg);

    void showProgressBar(boolean visible);
}