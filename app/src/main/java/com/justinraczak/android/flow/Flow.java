package com.justinraczak.android.flow;

import android.app.Application;
import android.util.Log;

import com.justinraczak.android.flow.data.Migration;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Justin on 5/23/17.
 */

public class Flow extends Application {

    private Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder()
                .name("flow-android.realm")
                .schemaVersion(2)
                .migration(new Migration())
                .build();

        try {
            Realm.migrateRealm(defaultConfig, new Migration());
        } catch (FileNotFoundException exception) {
            Log.d("Application", "Realm file not found for migration");
        }

        Realm.setDefaultConfiguration(defaultConfig);
        mRealm = Realm.getDefaultInstance();
        Log.d("Flow Application", "Realm configured and is " + mRealm);

        Log.d("Flow Application", "Initializing Joda Time");
        JodaTimeAndroid.init(this);
    }
}
