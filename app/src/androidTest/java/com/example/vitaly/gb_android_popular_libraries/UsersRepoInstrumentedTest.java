package com.example.vitaly.gb_android_popular_libraries;

import com.example.vitaly.gb_android_popular_libraries.di.DaggerTestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.TestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.modules.ApiModule;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;

public class UsersRepoInstrumentedTest {

    @Inject UsersRepo usersRepo;

    private static MockWebServer mockWebServer;

    @BeforeClass
    public static void setupClass() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        mockWebServer.shutdown();
    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule() {
                               @Override
                               public String getBaseUrl() {
                                   return mockWebServer.url("/").toString();
                               }
                           }).build();
        component.inject(this);
    }

    @Test
    public void getUserTest() {
        mockWebServer.enqueue(createUserResponse("someLogin", "avatarUrl", "reposUrl"));
        TestObserver<User> observer = new TestObserver<>();
        usersRepo.getUser("someUser").subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), "someLogin");
        assertEquals(observer.values().get(0).getAvatarUrl(), "avatarUrl");
        assertEquals(observer.values().get(0).getReposUrl(), "reposUrl");
    }

    @Test
    public void getUserReposTest() {
        List<Repository> tmpRepos = new ArrayList<>();
        tmpRepos.add(new Repository("123", "someName1"));
        tmpRepos.add(new Repository("456", "someName2"));
        tmpRepos.add(new Repository("789", "someName3"));

        User user = new User("someUser", "avatarUrl", "reposUrl");

        mockWebServer.enqueue(createUserReposResponse(tmpRepos));
        TestObserver<List<Repository>> observer = new TestObserver<>();

        usersRepo.getUserRepos(user).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        List<Repository> responseRepo = observer.values().get(0);
        for (int i = 0; i < responseRepo.size(); i++) {
            assertEquals(responseRepo.get(i).getId(), tmpRepos.get(i).getId());
            assertEquals(responseRepo.get(i).getName(), tmpRepos.get(i).getName());
        }
    }

    private MockResponse createUserResponse(String login, String avatarUrl, String reposUrl) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"login\":\"")
                .append(login)
                .append("\", \"avatar_url\":\"")
                .append(avatarUrl)
                .append("\", \"repos_url\":\"")
                .append(reposUrl)
                .append("\"}");

        return new MockResponse().setBody(sb.toString());
    }

    private MockResponse createUserReposResponse(List<Repository> repos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < repos.size(); i++) {
            String id = repos.get(i).getId();
            String name = repos.get(i).getName();

            sb.append("{\"id\":\"").append(id).append("\", \"name\":\"").append(name).append("\"}");

            if(repos.size() != 1 && i < repos.size() - 1)   {
                sb.append(",");
            }

            if(repos.size() == 1 || i == repos.size() - 1)   {
                sb.append("]");
            }
        }
        return new MockResponse().setBody(sb.toString());
    }
}
