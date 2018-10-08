package com.example.vitaly.gb_android_popular_libraries.model.cache.data;

import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.util.Hash;

import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Single;

public class PaperDataCache implements DataCache {

    @Override
    public void saveUser(User user, String username) {
        Paper.book("users").write(username, user);
    }

    @Override
    public Single<User> getUser(String username) {
        if (!Paper.book("users").contains(username)) {
            return Single.error(new RuntimeException("No such user in cache"));
        }
        return Single.fromCallable(() -> Paper.book("users").read(username));
    }

    @Override
    public void saveRepos(List<Repository> repos, User user) {
        Paper.book("repos").write(Hash.SHA1(user.getReposUrl()), repos);
    }

    @Override
    public Single<List<Repository>> getRepos(User user) {
        String sha1 = Hash.SHA1(user.getReposUrl());
        if (!Paper.book("repos").contains(sha1)) {
            return Single.error(new RuntimeException("No repos for such user in cache"));
        }
        return Single.fromCallable(() -> Paper.book("repos").read(sha1));
    }
}
