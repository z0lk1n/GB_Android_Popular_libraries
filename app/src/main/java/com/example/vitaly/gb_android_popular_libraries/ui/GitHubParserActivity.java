package com.example.vitaly.gb_android_popular_libraries.ui;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.model.image.IImageLoader;
import com.example.vitaly.gb_android_popular_libraries.model.image.android.GlideImageLoader;
import com.example.vitaly.gb_android_popular_libraries.presenter.GitHubParserPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GitHubParserActivity extends MvpAppCompatActivity implements GitHubParserView {

    @BindView(R.id.tv_username) TextView usernameTextView;
    @BindView(R.id.iv_avatar) ImageView avatarImageView;
    @BindView(R.id.rv_list_repos) RecyclerView recyclerView;

    private ReposAdapter adapter;
    private IImageLoader<ImageView> imageLoader;

    @InjectPresenter GitHubParserPresenter presenter;

    @ProvidePresenter
    public GitHubParserPresenter provideGitHubParserPresenter() {
        return new GitHubParserPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_parser);
        ButterKnife.bind(this);

        imageLoader = new GlideImageLoader();

        adapter = new ReposAdapter(presenter.getListPresenter());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUsernameText(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url) {
        imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void updateList() {
        adapter.refreshView();
    }
}