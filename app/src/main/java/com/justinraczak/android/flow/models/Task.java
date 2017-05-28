package com.justinraczak.android.flow.models;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Justin on 5/21/17.
 */

@IgnoreExtraProperties
public class Task extends RealmObject {

    private static final String LOG_TAG = Task.class.getSimpleName();

    public String userId;
    @PrimaryKey
    public int localRealmId;
    public String name;
    public String note;
    public String originalScheduledDate;
    public String currentScheduledDate;
    // Note this won't be passed into the constructor
    public int migrationCount;

    public Task() {
        // The default constructor
    }

    public Task(String userId, int localRealmId, String name, String note, String originalScheduledDate) {
        this.userId = userId;
        this.localRealmId = localRealmId;
        this.name = name;
        this.note = note;
        this.originalScheduledDate = originalScheduledDate;
        this.currentScheduledDate = originalScheduledDate;
        this.migrationCount = 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOriginalScheduledDate() {
        return originalScheduledDate;
    }

    public void setOriginalScheduledDate(String originalScheduledDate) {
        this.originalScheduledDate = originalScheduledDate;
    }

    public String getCurrentScheduledDate() {
        return currentScheduledDate;
    }

    public void setCurrentScheduledDate(String currentScheduledDate) {
        this.currentScheduledDate = currentScheduledDate;
    }

    public int getMigrationCount() {
        return migrationCount;
    }

    public void setMigrationCount(int migrationCount) {
        this.migrationCount = migrationCount;
    }

    public int getLocalRealmId() {
        return localRealmId;
    }

    public void setLocalRealmId(int localRealmId) {
        this.localRealmId = localRealmId;
    }

    public void incrementMigrations() {
        this.migrationCount += 1;
    }

    public static Integer getNewAutoIncrementId() {

        Realm realm = Realm.getDefaultInstance();
        //Integer oldMaxId = realm.where(Set.class).max("localRealmId").intValue();

        if (realm.where(Task.class).max("localRealmId") == null) {
            Log.d(LOG_TAG,"max task table id was null, returning 1");
            realm.close();
            return 1;
        } else {
            Log.d(LOG_TAG, "incrementing max task table id");
            realm.close();
            return (realm.where(Task.class).max("localRealmId").intValue()+1);
        }
    }
}
