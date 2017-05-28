package com.justinraczak.android.flow;

import android.app.Application;
import android.util.Log;

import net.danlew.android.joda.JodaTimeAndroid;

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
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(defaultConfig);
        mRealm = Realm.getDefaultInstance();
        Log.d("Flow Application", "Realm configured and is " + mRealm);

        Log.d("Flow Application", "Initializing Joda Time");
        JodaTimeAndroid.init(this);
    }
}
