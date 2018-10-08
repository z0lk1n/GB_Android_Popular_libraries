package com.example.vitaly.gb_android_popular_libraries.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.presenter.MainPresenter;
import com.example.vitaly.gb_android_popular_libraries.ui.convertimage.ConvertImageActivity;
import com.example.vitaly.gb_android_popular_libraries.ui.countbuttons.CountButtonsActivity;
import com.example.vitaly.gb_android_popular_libraries.ui.githubparser.GitHubParserActivity;
import com.example.vitaly.gb_android_popular_libraries.ui.textflow.TextFlowActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.btn_count_buttons) Button buttonCountButtons;
    @BindView(R.id.btn_text_flow) Button buttonTextFlow;
    @BindView(R.id.btn_convert_image) Button buttonConvertImage;
    @BindView(R.id.btn_github_parser) Button buttonGitHubParser;

    @InjectPresenter MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
    }

    @OnClick(R.id.btn_count_buttons)
    public void onClickCountButtons() {
        startActivity(new Intent(MainActivity.this, CountButtonsActivity.class));
    }

    @OnClick(R.id.btn_text_flow)
    public void onClickTextFlow() {
        startActivity(new Intent(MainActivity.this, TextFlowActivity.class));
    }

    @OnClick(R.id.btn_convert_image)
    public void onClickConvertImage() {
        startActivity(new Intent(MainActivity.this, ConvertImageActivity.class));
    }

    @OnClick(R.id.btn_github_parser)
    public void onClickGithubParser() {
        startActivity(new Intent(MainActivity.this, GitHubParserActivity.class));
    }
}
