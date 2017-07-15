package com.justinraczak.android.flow.data;

import java.util.Date;
import java.util.StringTokenizer;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
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

        // Set up schema version 4 - add User and UserSession classes
        if (oldVersion == 3) {
            realmSchema.create("User")
                    .addField("id", String.class)
                    .addField("createdAt", Date.class)
                    .addField("email", String.class)
                    .addField("name", String.class)
                    //.addRealmObjectField("currentSession", realmSchema.get("UserSession"))
                    .addField("lastSignInAt", Date.class)
                    .addField("apiUid", String.class);
            realmSchema.create("UserSession")
                    .addField("isValid", Boolean.class)
                    .addField("createdAt", Date.class)
                    .addField("tokenUpdatedAt", Date.class)
                    .addRealmObjectField("user", realmSchema.get("User"))
                    .addField("apiUid", String.class)
                    .addField("apiAccessToken", String.class)
                    .addField("apiClient", String.class)
                    .addField("apiTokenType", String.class)
                    .addField("apiExpiry", String.class);
            oldVersion++;
        }

        if (oldVersion == 4) {
            // Add current session relation to users
            // Set the values to nullable false for all required fields
            realmSchema.get("UserSession")
                    .setNullable("isValid", false)
                    .setNullable("createdAt", false)
                    .setNullable("tokenUpdatedAt", false)
                    .setNullable("apiUid", false)
                    .setNullable("apiAccessToken", false)
                    .setNullable("apiClient", false)
                    .setNullable("apiTokenType", false)
                    .setNullable("apiExpiry", false);
            realmSchema.get("User")
                    .addRealmObjectField("currentSession", realmSchema.get("UserSession"))
                    .setNullable("apiUid", false);
            oldVersion++;
        }
        if (oldVersion == 5) {
            RealmObjectSchema schema = realmSchema.get("User");
            schema
                    .addPrimaryKey("apiUid")
                    .addField("idTemp", int.class);
            schema.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    String currentId = obj.getString("id");
                    obj.setInt("idTemp", Integer.valueOf(currentId));
                }
            });
            schema.removeField("id");
            schema.renameField("idTemp", "id");
            oldVersion++;
        }

        if (oldVersion == 6) {
            RealmObjectSchema schema = realmSchema.get("User");
            schema
                    .addField("idTemp", int.class);
            schema.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    String currentId = obj.getString("id");
                    obj.setInt("idTemp", Integer.valueOf(currentId));
                }
            });
            schema.removeField("id");
            schema.renameField("idTemp", "id");
            oldVersion++;
        }
    }
}
