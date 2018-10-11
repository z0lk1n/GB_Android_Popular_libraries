package com.example.vitaly.gb_android_popular_libraries.model.cache.data;

import com.example.vitaly.gb_android_popular_libraries.model.entity.Repository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.User;
import com.example.vitaly.gb_android_popular_libraries.model.entity.realm.RealmRepository;
import com.example.vitaly.gb_android_popular_libraries.model.entity.realm.RealmUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.realm.Realm;

public class RealmDataCache implements DataCache {

    @Override
    public void saveUser(User user, String username) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();
        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = innerRealm.createObject(RealmUser.class, username);
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });
        } else {
            realm.executeTransaction(innerRealm -> {
                realmUser.setAvatarUrl(user.getAvatarUrl());
                realmUser.setReposUrl(user.getReposUrl());
            });
        }
        realm.close();
    }

    @Override
    public Single<User> getUser(String username) {
        return Single.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();
            if (realmUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                emitter.onSuccess(new User(realmUser.getLogin(), realmUser.getAvatarUrl(), realmUser.getReposUrl()));
            }
            realm.close();
        });
    }

    @Override
    public void saveRepos(List<Repository> repos, User user) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });
        }
        realm.executeTransaction(innerRealm -> {
            realmUser.getRepos().deleteAllFromRealm();
            for (Repository repository : repos) {
                RealmRepository realmRepository = innerRealm.createObject(RealmRepository.class, repository.getId());
                realmRepository.setName(repository.getName());
                realmUser.getRepos().add(realmRepository);
            }
        });
        realm.close();
    }

    @Override
    public Single<List<Repository>> getRepos(User user) {
        return Single.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
            if (realmUser == null) {
                emitter.onError(new RuntimeException("No repos for such user in cache"));
            } else {
                List<Repository> repositories = new ArrayList<>();
                for (RealmRepository realmRepository : realmUser.getRepos()) {
                    repositories.add(new Repository(realmRepository.getId(), realmRepository.getName()));
                }
                emitter.onSuccess(repositories);
            }
            realm.close();
        });
    }
}
