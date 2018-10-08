package com.example.vitaly.gb_android_popular_libraries.di;

import com.example.vitaly.gb_android_popular_libraries.di.modules.AppModule;
import com.example.vitaly.gb_android_popular_libraries.di.modules.FileConverterModule;
import com.example.vitaly.gb_android_popular_libraries.di.modules.ImageLoaderModule;
import com.example.vitaly.gb_android_popular_libraries.di.modules.RepoModule;
import com.example.vitaly.gb_android_popular_libraries.presenter.ConvertImagePresenter;
import com.example.vitaly.gb_android_popular_libraries.presenter.GitHubParserPresenter;
import com.example.vitaly.gb_android_popular_libraries.ui.convertimage.ConvertImageActivity;
import com.example.vitaly.gb_android_popular_libraries.ui.githubparser.GitHubParserActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        RepoModule.class,
        ImageLoaderModule.class,
        FileConverterModule.class})
public interface AppComponent {

    void inject(GitHubParserPresenter gitHubParserPresenter);

    void inject(GitHubParserActivity gitHubParserActivity);

    void inject(ConvertImagePresenter convertImagePresenter);

    void inject(ConvertImageActivity convertImageActivity);
}
