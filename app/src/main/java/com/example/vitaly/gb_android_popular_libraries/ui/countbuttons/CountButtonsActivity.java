package com.example.vitaly.gb_android_popular_libraries.ui.countbuttons;

import android.os.Bundle;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.presenter.CountButtonsPresenter;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProviderImpl;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class CountButtonsActivity extends MvpAppCompatActivity implements CountButtonsView {

    @BindView(R.id.btn_one) Button buttonOne;
    @BindView(R.id.btn_two) Button buttonTwo;
    @BindView(R.id.btn_three) Button buttonThree;

    @InjectPresenter CountButtonsPresenter presenter;

    @ProvidePresenter
    public CountButtonsPresenter provideMainPresenter() {
        return new CountButtonsPresenter(new SchedulersProviderImpl());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_buttons);
        ButterKnife.bind(CountButtonsActivity.this);
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onClick(Button button) {
        presenter.counterClick(button.getId());
    }

    @Override
    public void setButtonText(int id, Integer calculate) {
        switch (id) {
            case R.id.btn_one:
                buttonOne.setText(String.format(Locale.getDefault(), "%d", calculate));
                break;
            case R.id.btn_two:
                buttonTwo.setText(String.format(Locale.getDefault(), "%d", calculate));
                break;
            case R.id.btn_three:
                buttonThree.setText(String.format(Locale.getDefault(), "%d", calculate));
                break;
        }
    }
}
