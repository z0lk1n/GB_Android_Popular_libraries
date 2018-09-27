package com.example.vitaly.gb_android_popular_libraries.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repos;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;
import com.example.vitaly.gb_android_popular_libraries.ui.GitHubParserView;
import com.example.vitaly.gb_android_popular_libraries.ui.ReposRowView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class GitHubParserPresenter extends MvpPresenter<GitHubParserView> {
    private final String username = "z0lk1n";

    public class ReposListPresenter {
        private List<Repos> reposList;

        ReposListPresenter() {
            this.reposList = new ArrayList<>();
        }

        public void bindViewAt(int position, ReposRowView view) {
            view.setReposName(reposList.get(position).getName());
        }

        public int getReposCount() {
            return reposList.size();
        }
    }

    private UsersRepo usersRepo;
    private Scheduler mainThreadScheduler;
    private ReposListPresenter listPresenter;

    public GitHubParserPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.usersRepo = new UsersRepo();
        this.listPresenter = new ReposListPresenter();
    }

    public ReposListPresenter getListPresenter() {
        return listPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        usersRepo.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {
                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());

                    usersRepo.getUserRepos()
                            .subscribeOn(Schedulers.io())
                            .observeOn(mainThreadScheduler)
                            .subscribe(list -> {
                                listPresenter.reposList = list;
                                getViewState().updateList();
                            });
                }, throwable -> Timber.e(throwable, "Failed to get user"));
    }
}
