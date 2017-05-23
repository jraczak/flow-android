package com.justinraczak.android.flow.data;

/**
 * Created by Justin on 5/23/17.
 */

public class UserContract {

    private UserContract() {
    }

    public static class UsersEntry {
        public static final String TABLE_NAME = "users";

        public static final String COLUMN_USER_ID = "id";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_SIGNUP_DATE = "signupDate";

    }
}
