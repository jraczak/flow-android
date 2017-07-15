package com.justinraczak.android.flow.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 5/21/17.
 */

public class User extends RealmObject {

    public int id;
    public Date createdAt;
    public String email;
    public String name;
    public UserSession currentSession;
    public Date lastSignInAt;
    @Required @PrimaryKey
    private String apiUid;

    public User() {
        // Obligatory empty constructor
    }

    public User(int id, String email, String name, String apiUid) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.apiUid = apiUid;

        this.createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(UserSession currentSession) {
        this.currentSession = currentSession;
    }

    public Date getLastSignInAt() {
        return lastSignInAt;
    }

    public void setLastSignInAt(Date lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }

    public String getApiUid() {
        return apiUid;
    }

    public void setApiUid(String apiUid) {
        this.apiUid = apiUid;
    }
}
