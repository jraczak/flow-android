package com.justinraczak.android.flow.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Justin on 5/21/17.
 */

@IgnoreExtraProperties
public class Task {

    public String name;
    public String note;
    public String originalScheduledDate;
    public String currentScheduledDate;
    // Note this won't be passed into the constructor
    public int migrationCount;

    public Task() {
        // The default constructor
    }

    public Task(String name, String note, String originalScheduledDate) {
        this.name = name;
        this.note = note;
        this.originalScheduledDate = originalScheduledDate;
        this.currentScheduledDate = originalScheduledDate;
        this.migrationCount = 0;
    }
}
