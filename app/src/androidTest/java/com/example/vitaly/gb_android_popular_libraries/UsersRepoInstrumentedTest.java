package com.example.vitaly.gb_android_popular_libraries;

import com.example.vitaly.gb_android_popular_libraries.di.DaggerTestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.TestComponent;
import com.example.vitaly.gb_android_popular_libraries.di.modules.ApiModule;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.model.repo.UsersRepo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

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
                           }
                ).build();
        component.inject(this);
    }

    @Test
    public void getUser() {
        mockWebServer.enqueue(createUserResponse("someuser", "someurl"));
        TestObserver<User> observer = new TestObserver<>();
        usersRepo.getUser("somelogin").subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), "someuser");
        assertEquals(observer.values().get(0).getAvatarUrl(), "someurl");

    }

    private MockResponse createUserResponse(String login, String avatarUrl) {
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatarUrl + "\"}";
        return new MockResponse()
                .setBody(body);
    }
}
