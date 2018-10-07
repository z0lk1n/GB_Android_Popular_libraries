package com.example.vitaly.gb_android_popular_libraries.ui.textflow;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.model.Event;
import com.example.vitaly.gb_android_popular_libraries.presenter.TextFlowPresenter;
import com.example.vitaly.gb_android_popular_libraries.util.EventBus;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public final class TextFlowActivity extends MvpAppCompatActivity implements TextFlowView {

    @BindView(R.id.txt_view) TextView textView;
    @BindView(R.id.edit_txt) EditText editText;

    @InjectPresenter TextFlowPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_flow);
        ButterKnife.bind(TextFlowActivity.this);
        initRxBinding();
        initEventBus();
    }

    private void initRxBinding() {
        RxTextView.textChanges(editText).subscribe(charSequence -> presenter.textChanged(charSequence.toString()));
    }

    private void initEventBus() {
        Observable<String> sourceFirst = Observable.just("data1", "data11", "data111");
        Observable<String> sourceSecond = Observable.just("data2", "data22", "data222");

        Observer<String> observerFirst = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                Timber.d("observerFirst: %s", s);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }

            @Override
            public void onComplete() {
                Timber.d("observerFirst onCompleted!");
            }
        };

        Observer<String> observerSecond = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                Timber.d("observerSecond: %s", s);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }

            @Override
            public void onComplete() {
                Timber.d("observerSecond onCompleted!");
            }
        };

        EventBus<String> eventBus = new EventBus<>(sourceFirst, sourceSecond);
        eventBus.register(observerFirst);
        eventBus.register(observerSecond);
        eventBus.publish(new Event("Event on EventBus"));
    }

    @Override
    public void setTextInTextView(String s) {
        textView.setText(s);
    }
}
