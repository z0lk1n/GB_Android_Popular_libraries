package com.example.vitaly.gb_android_popular_libraries.ui.textflow;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface TextFlowView extends MvpView {

    void setTextInTextView(String s);
}
