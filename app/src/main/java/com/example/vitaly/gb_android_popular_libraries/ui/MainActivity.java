package com.example.vitaly.gb_android_popular_libraries.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.presenter.MainPresenter;
import com.example.vitaly.gb_android_popular_libraries.util.Event;
import com.example.vitaly.gb_android_popular_libraries.util.EventBus;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManagerImpl;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProviderImpl;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int PICK_IMAGE = 1;
    private FileConverterManager converter;
    private File storageDir;

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
    @BindView(R.id.convert_txt_view)
    TextView convertTextView;
    @BindView(R.id.convert_btn)
    Button convertButton;
    @BindView(R.id.convert_img_view)
    ImageView imageView;

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        converter = new FileConverterManagerImpl(storageDir);
        presenter = new MainPresenter(new SchedulersProviderImpl(), converter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        initRxBinding();
        initEventBus();
        storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @OnClick(R.id.convert_btn)
    public void onClickConvertBtn() {
        presenter.chooseImageClick();
    }

    @Override
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                InputStream stream = getContentResolver().openInputStream(data.getData());
                presenter.sendByteArrayFromRequest(converter.getByteArrayFromStream(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setImageOnView(String path) {
        imageView.setImageURI(Uri.fromFile(new File(path)));
    }

    @Override
    public void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Convert image file")
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onClick(Button button) {
        presenter.counterClick(button.getId());
    }

    private void initRxBinding() {
        RxTextView.textChanges(editText).subscribe(charSequence -> presenter.textChanged(charSequence.toString()));
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

    @Override
    public void setTextInTextView(String s) {
        textView.setText(s);
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
}
