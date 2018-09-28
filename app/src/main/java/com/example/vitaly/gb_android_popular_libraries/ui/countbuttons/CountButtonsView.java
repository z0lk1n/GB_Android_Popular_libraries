package com.example.vitaly.gb_android_popular_libraries.ui.countbuttons;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface CountButtonsView extends MvpView {

    void setButtonText(int id, Integer calculate);
}
