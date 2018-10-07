package com.example.vitaly.gb_android_popular_libraries.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "repositories")
public class AARepository extends Model {

    @Column(name = "github_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "user")
    private AAUser user;

    public String getGithubId() {
        return id;
    }

    public void setGithubId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AAUser getUser() {
        return user;
    }

    public void setUser(AAUser user) {
        this.user = user;
    }
}
