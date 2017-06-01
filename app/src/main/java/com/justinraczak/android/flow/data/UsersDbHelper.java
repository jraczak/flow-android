package com.justinraczak.android.flow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Justin on 5/23/17.
 */

public class UsersDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";

    private static final int DATABASE_VERSION = 1;

    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
                UserContract.UserEntry.TABLE_NAME + " (" +
                UserContract.UserEntry.COLUMN_USER_ID + " TEXT NOT NULL," +
                UserContract.UserEntry.COLUMN_SIGNUP_DATE + " TEXT NOT NULL," +
                UserContract.UserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL," +
                UserContract.UserEntry.COLUMN_USER_NAME + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }
}
