package com.example.vitaly.gb_android_popular_libraries;

import com.example.vitaly.gb_android_popular_libraries.di.DaggerTestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.TestComponent;
import com.example.vitaly.gb_android_popular_libraries.model.cache.data.DataCache;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

public class AADataCacheInstrumentedTest {

    @Inject @Named("aaCache") DataCache cache;

    @BeforeClass
    public static void setupClass() {


    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent.builder().build();
        component.inject(this);
    }

    @After
    public void after() {

    }

    @Test
    public void userTest() {
        User user = new User("someLogin", "avatarUrl", "reposUrl");

        cache.saveUser(user, user.getLogin());

        TestObserver<User> observer = new TestObserver<>();
        cache.getUser(user.getLogin()).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), user.getLogin());
        assertEquals(observer.values().get(0).getAvatarUrl(), user.getAvatarUrl());
        assertEquals(observer.values().get(0).getReposUrl(), user.getReposUrl());
    }

    @Test
    public void reposTest() {
        List<Repository> tmpRepos = new ArrayList<>();
        tmpRepos.add(new Repository("45345345", "someName1"));
        tmpRepos.add(new Repository("3452345234645634", "someName2"));
        tmpRepos.add(new Repository("324523452345", "someName3"));

        User user = new User("someLogin", "avatarUrl", "reposUrl");

        cache.saveRepos(tmpRepos, user);

        TestObserver<List<Repository>> observer = new TestObserver<>();
        cache.getRepos(user).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        List<Repository> responseRepo = observer.values().get(0);
        for (int i = 0; i < responseRepo.size(); i++) {
            assertEquals(responseRepo.get(i).getId(), tmpRepos.get(i).getId());
            assertEquals(responseRepo.get(i).getName(), tmpRepos.get(i).getName());
        }
    }
}
