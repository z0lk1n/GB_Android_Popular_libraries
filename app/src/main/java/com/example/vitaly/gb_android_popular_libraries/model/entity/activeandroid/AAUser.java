package com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "users")
public class AAUser extends Model {

    @Column(name = "login")
    private String login;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "repos_url")
    private String reposUrl;

    public List<AARepository> repositories() {
        return getMany(AARepository.class, "user");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }
}
