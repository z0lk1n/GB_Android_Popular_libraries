package com.example.vitaly.gb_android_popular_libraries;

import com.example.vitaly.gb_android_popular_libraries.di.DaggerTestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.TestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.modules.TestRepoModule;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;
import com.example.vitaly.gb_android_popular_libraries.presenter.GitHubParserPresenter;
import com.example.vitaly.gb_android_popular_libraries.ui.githubparser.GitHubParserView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

public class GitHubParserPresenterUnitTest {

    private GitHubParserPresenter presenter;
    private TestScheduler testScheduler;

    @Mock GitHubParserView view;

    @BeforeClass
    public static void setupClass() {

    }

    @AfterClass
    public static void tearDown() {

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = Mockito.spy(new GitHubParserPresenter(testScheduler));
    }

    @After
    public void after() {

    }

    @Test
    public void loadDataTestSuccess() {
        User user = new User("googlesamples", "avatarUrl", "someUrl");
        TestComponent component = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public UsersRepo usersRepo() {
                        UsersRepo repo = super.usersRepo();
                        Mockito.when(repo.getUser("googlesamples")).thenReturn(Single.just(user));
                        Mockito.when(repo.getUserRepos(user)).thenReturn(Single.just(new ArrayList<>()));
                        return repo;
                    }
                }).build();

        component.inject(presenter);
        presenter.attachView(view);
        Mockito.verify(presenter).loadData();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(view).showProgressBar(false);
        Mockito.verify(view).loadImage(user.getAvatarUrl());
        Mockito.verify(view).setUsernameText(user.getLogin());
        Mockito.verify(view).updateRepoList();
    }

    @Test
    public void loadDataTestFailure() {

    }
}
