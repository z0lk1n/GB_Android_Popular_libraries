package com.example.vitaly.gb_android_popular_libraries;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.EventBus.Event;
import com.example.vitaly.gb_android_popular_libraries.EventBus.EventBus;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.btn_one)
    Button buttonOne;
    @BindView(R.id.btn_two)
    Button buttonTwo;
    @BindView(R.id.btn_three)
    Button buttonThree;
    @BindView(R.id.txt_view)
    TextView textView;
    @BindView(R.id.edit_txt)
    EditText editText;

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(new SchedulersProviderImpl());
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        initRxBinding();
        initEventBus();
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onClick(Button button) {
        presenter.counterClick(button.getId());
    }

    @Override
    public void setButtonText(int id, Integer val) {
        switch (id) {
            case R.id.btn_one:
                buttonOne.setText(String.format(Locale.getDefault(), "%d", val));
                break;
            case R.id.btn_two:
                buttonTwo.setText(String.format(Locale.getDefault(), "%d", val));
                break;
            case R.id.btn_three:
                buttonThree.setText(String.format(Locale.getDefault(), "%d", val));
                break;
        }
    }

    private void initRxBinding() {
        RxTextView.textChanges(editText).subscribe(charSequence -> textView.setText(charSequence));
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
        eventBus.publish(new Event<>("Event on EventBus"));
        eventBus.register(observerFirst);
        eventBus.register(observerSecond);
    }
}
