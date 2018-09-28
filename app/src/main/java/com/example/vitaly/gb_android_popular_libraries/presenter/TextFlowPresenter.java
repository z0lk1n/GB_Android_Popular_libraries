package com.example.vitaly.gb_android_popular_libraries.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.ui.textflow.TextFlowView;

@InjectViewState
public class TextFlowPresenter extends MvpPresenter<TextFlowView> {

    public TextFlowPresenter() {
    }

    public void textChanged(String s) {
        getViewState().setTextInTextView(s);
    }
}
