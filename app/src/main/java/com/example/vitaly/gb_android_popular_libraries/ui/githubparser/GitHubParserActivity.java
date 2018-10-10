package com.example.vitaly.gb_android_popular_libraries.ui.githubparser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.App;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.model.image.ImageLoader;
import com.example.vitaly.gb_android_popular_libraries.presenter.GitHubParserPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class GitHubParserActivity extends MvpAppCompatActivity implements GitHubParserView {

    @BindView(R.id.tv_username) TextView usernameTextView;
    @BindView(R.id.iv_avatar) ImageView avatarImageView;
    @BindView(R.id.rv_list_repos) RecyclerView recyclerView;
    @BindView(R.id.fab_github_parser) FloatingActionButton fab;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private ReposAdapter adapter;

    @Inject ImageLoader<ImageView> imageLoader;

    @InjectPresenter GitHubParserPresenter presenter;

    @ProvidePresenter
    public GitHubParserPresenter provideGitHubParserPresenter() {
        GitHubParserPresenter presenter = new GitHubParserPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_parser);

        App.getInstance().getAppComponent().inject(this);

        ButterKnife.bind(this);

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
    public void updateRepoList() {
        adapter.refreshView();
    }

    @Override
    public void showChooseUserDialog() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(input)
                .setTitle(R.string.input_username)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    String username = input.getText().toString().trim();
                    if (!username.isEmpty()) {
                        presenter.setUsername(username);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @OnClick(R.id.fab_github_parser)
    public void onClickFab() {
        presenter.chooseUser();
    }

    @Override
    public void showNotifyingMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}