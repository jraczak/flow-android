package com.justinraczak.android.flow;

import android.app.Application;
import android.util.Log;

import com.justinraczak.android.flow.data.Migration;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Justin on 5/23/17.
 */

public class Flow extends Application {

    private Realm mRealm;
    //private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Application", "Calling Realm.init");
        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder()
                .name("flow-android.realm")
                .schemaVersion(3)
                .migration(new Migration())
                .build();

        //try {
        //    Realm.migrateRealm(defaultConfig, new Migration());
        //} catch (FileNotFoundException exception) {
        //    Log.d("Application", "Realm file not found for migration");
        //}

        Realm.setDefaultConfiguration(defaultConfig);
        mRealm = Realm.getDefaultInstance();
        Log.d("Flow Application", "Realm configured and is " + mRealm);

        Log.d("Flow Application", "Initializing Joda Time");
        JodaTimeAndroid.init(this);

        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    //TODO: Figure out how to make this global
    //public FirebaseAnalytics getmFirebaseAnalytics() {
    //    return mFirebaseAnalytics;
    //}
}
