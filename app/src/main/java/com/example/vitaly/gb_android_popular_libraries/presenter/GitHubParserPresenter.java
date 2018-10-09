package com.example.vitaly.gb_android_popular_libraries.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;
import com.example.vitaly.gb_android_popular_libraries.ui.githubparser.GitHubParserView;
import com.example.vitaly.gb_android_popular_libraries.ui.githubparser.ReposRowView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import timber.log.Timber;

@InjectViewState
public class GitHubParserPresenter extends MvpPresenter<GitHubParserView> {

    public final class ReposListPresenter {

        private List<Repository> repositoryList;

        ReposListPresenter() {
            this.repositoryList = new ArrayList<>();
        }

        public void bindViewAt(int position, @NotNull ReposRowView view) {
            view.setRepoName(repositoryList.get(position).getName());
        }

        public int getReposCount() {
            return repositoryList.size();
        }
    }

    private static final String MSG_ERROR_USER = "Failed to get user";
    private static final String MSG_ERROR_REPOS = "Failed to get user repos";

    private String username = "z0lk1n";
    private Scheduler mainThreadScheduler;
    private ReposListPresenter listPresenter;

    @Inject UsersRepo usersRepo;

    public GitHubParserPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.listPresenter = new ReposListPresenter();
    }

    @Contract(pure = true)
    public ReposListPresenter getListPresenter() {
        return listPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    @SuppressLint("CheckResult")
    public void loadData() {
        getViewState().showProgressBar(true);
        usersRepo.getUser(username)
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {
                    getViewState().showProgressBar(false);
                    getViewState().loadImage(user.getAvatarUrl());
                    getViewState().setUsernameText(user.getLogin());
                    usersRepo.getUserRepos(user)
                            .observeOn(mainThreadScheduler)
                            .subscribe(repositories -> {
                                listPresenter.repositoryList = repositories;
                                getViewState().updateRepoList();
                            }, throwable -> {
                                Timber.e(throwable, MSG_ERROR_REPOS);
                                getViewState().showNotifyingMessage(throwable.getMessage());
                            });
                }, throwable -> {
                    getViewState().showProgressBar(false);
                    Timber.e(throwable, MSG_ERROR_USER);
                    getViewState().showNotifyingMessage(throwable.getMessage());
                });
    }

    public void setUsername(String username) {
        this.username = username;
        loadData();
    }

    public void chooseUser() {
        getViewState().showChooseUserDialog();
    }
}
