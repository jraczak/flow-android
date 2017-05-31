package com.justinraczak.android.flow.data;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by Justin on 5/29/17.
 */

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();

        // Set up schema version 2 - add complete field and set as required
        if (oldVersion == 1) {
            RealmObjectSchema taskSchema = realmSchema.get("Task");
            // Add the complete state boolean field
            taskSchema.addField("complete", Boolean.class, FieldAttribute.REQUIRED);
            oldVersion++;
        }

        // Set up schema version 3 - set fields on Task to required
        if (oldVersion == 2) {
            RealmObjectSchema taskSchema = realmSchema.get("Task");
            // Add new annotations
            taskSchema.setNullable("userId", false);
            taskSchema.setNullable("name", false);
            taskSchema.setNullable("originalScheduledDate", false);
            taskSchema.setNullable("currentScheduledDate", false);
            oldVersion++;
        }
    }
}
