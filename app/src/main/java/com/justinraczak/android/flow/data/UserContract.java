package com.justinraczak.android.flow.data;

import android.net.Uri;

/**
 * Created by Justin on 5/23/17.
 */

public class UserContract {

    private UserContract() {
    }

    public static final String AUTHORITY = "com.justinraczak.android.flow";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_USERS = "users";

    public static class UserEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();

        public static final String TABLE_NAME = "users";

        public static final String COLUMN_USER_ID = "id";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_SIGNUP_DATE = "signupDate";

    }
}
