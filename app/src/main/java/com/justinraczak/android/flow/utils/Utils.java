package com.justinraczak.android.flow.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.justinraczak.android.flow.models.User;

/**
 * Created by Justin on 5/15/17.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    private static final String PREFERENCES_FILE = "flow_settings";

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void writeNewUserToFirebase(String id, String email) {
        Log.d(TAG, "Attempting to save new user to Firebase");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        User user = new User(id, email);
        databaseReference.child("users").child(id).setValue(user);
    }
}
