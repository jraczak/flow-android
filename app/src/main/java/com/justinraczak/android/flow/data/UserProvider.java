package com.justinraczak.android.flow.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Justin on 5/31/17.
 */

public class UserProvider extends ContentProvider {

    private UsersDbHelper mUsersDbHelper;

    public static final int USERS = 100;
    public static final int USER_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(UserContract.AUTHORITY, UserContract.PATH_USERS, USERS);
        uriMatcher.addURI(UserContract.AUTHORITY, UserContract.PATH_USERS + "/*", USER_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mUsersDbHelper = new UsersDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mUsersDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor returnedCursor;

        switch (match) {
            case USERS:
                returnedCursor = db.query(UserContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case USER_WITH_ID:
                //Get the id value, index 0 would be the root /users segment
                String id = uri.getPathSegments().get(1);
                // Questin mark to indicate to get the value from the mSelectionArgs variable
                String mSelection = "id=?";
                String[] mSelectionArgs = new String[]{id};
                returnedCursor = db.query(UserContract.UserEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("This database operation isn't supported");
        }
        returnedCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnedCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mUsersDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnedUri;

        switch (match) {
            case USERS:
                long id = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnedUri = ContentUris.withAppendedId(UserContract.UserEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
