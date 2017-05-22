package com.justinraczak.android.flow.models;

/**
 * Created by Justin on 5/21/17.
 */

public class User {

    public String id;
    public String email;

    public User() {
        // Obligatory empty constructor
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
