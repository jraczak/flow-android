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

        if (oldVersion == 1) {
            RealmObjectSchema taskSchema = realmSchema.get("Task");
            // Add the complete state boolean field
            taskSchema.addField("complete", Boolean.class, FieldAttribute.REQUIRED);
        }
        oldVersion++;
    }
}
