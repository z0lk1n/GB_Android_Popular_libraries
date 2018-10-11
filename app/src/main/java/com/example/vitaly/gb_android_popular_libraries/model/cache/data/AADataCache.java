package com.example.vitaly.gb_android_popular_libraries.model.cache.data;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid.AARepository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid.AAUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class AADataCache implements DataCache {

    @Override
    public void saveUser(User user, String username) {
        AAUser aaUser = new Select()
                .from(AAUser.class)
                .where("login = ?", username)
                .executeSingle();
        if (aaUser == null) {
            aaUser = new AAUser();
            aaUser.setLogin(username);
        }
        aaUser.setAvatarUrl(user.getAvatarUrl());
        aaUser.setReposUrl(user.getReposUrl());
        aaUser.save();
    }

    @Override
    public Single<User> getUser(String username) {
        return Single.create(emitter -> {
            AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", username)
                    .executeSingle();
            if (aaUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                emitter.onSuccess(new User(aaUser.getLogin(), aaUser.getAvatarUrl(), aaUser.getReposUrl()));
            }
        });
    }

    @Override
    public void saveRepos(List<Repository> repos, User user) {
        AAUser aaUser = new Select()
                .from(AAUser.class)
                .where("login = ?", user.getLogin())
                .executeSingle();
        if (aaUser == null) {
            aaUser = new AAUser();
            aaUser.setLogin(user.getLogin());
            aaUser.setAvatarUrl(user.getAvatarUrl());
            aaUser.setReposUrl(user.getReposUrl());
        }
        new Delete().from(AARepository.class).where("user = ?", aaUser.getId()).execute();
        ActiveAndroid.beginTransaction();
        try {
            for (Repository repository : repos) {
                AARepository aaRepository = new AARepository();
                aaRepository.setGithubId(repository.getId());
                aaRepository.setName(repository.getName());
                aaRepository.setUser(aaUser);
                aaRepository.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public Single<List<Repository>> getRepos(User user) {
        return Single.create(emitter -> {
            AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", user.getLogin())
                    .executeSingle();
            if (aaUser == null) {
                emitter.onError(new RuntimeException("No repos for such user in cache"));
            } else {
                List<Repository> repos = new ArrayList<>();
                for (AARepository aaRepository : aaUser.repositories()) {
                    repos.add(new Repository(aaRepository.getGithubId(), aaRepository.getName()));
                }
                emitter.onSuccess(repos);
            }
        });
    }
}
