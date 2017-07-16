package com.justinraczak.android.flow;

import android.app.Application;
import android.util.Log;

import com.justinraczak.android.flow.data.Migration;
import com.justinraczak.android.flow.models.UserSession;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Justin on 5/23/17.
 */

public class Flow extends Application {

    private Realm mRealm;
    //private FirebaseAnalytics mFirebaseAnalytics;

    private UserSession currentUserSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Application", "Calling Realm.init");
        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder()
                .name("flow-android.realm")
                .schemaVersion(7)
                .migration(new Migration())
                .build();

        // Userful snippet for logging the current realm version on a device
        //DynamicRealm dynRealm = DynamicRealm.getInstance(defaultConfig);
        //Log.d("Application realm check", String.valueOf(dynRealm.getVersion()));//this will return the existing schema version
        //dynRealm.close();

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


    public UserSession getCurrentUserSession() {
        return currentUserSession;
    }

    public void setCurrentUserSession(UserSession currentUserSession) {
        this.currentUserSession = currentUserSession;
        Log.d("Application", "Set current user session to " + currentUserSession);
    }
}
