package com.example.vitaly.gb_android_popular_libraries.di;

import com.example.vitaly.gb_android_popular_libraries.di.modules.TestRepoModule;
import com.example.vitaly.gb_android_popular_libraries.presenter.GitHubParserPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestRepoModule.class})
public interface TestComponent {
    void inject(GitHubParserPresenter presenter);
}

