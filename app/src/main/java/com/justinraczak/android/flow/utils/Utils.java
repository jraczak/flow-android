package com.justinraczak.android.flow.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.justinraczak.android.flow.models.Task;
import com.justinraczak.android.flow.models.User;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Justin on 5/15/17.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    private static final String PREFERENCES_FILE = "flow_settings";

    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy");

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

    public static void writeNewTaskToFirebase(Task task) {
        Log.d(TAG, "Attempting to save new task to Firebase");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(task.getUserId()).child("tasks")
                .child(String.valueOf(task.getLocalRealmId()))
                .setValue(task);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {
            int numberOfItems = listAdapter.getCount();
            int totalItemsHeight = 0;

            for (int itemPosition = 0; itemPosition < numberOfItems; itemPosition++) {
                View item = listAdapter.getView(itemPosition, null, listView);
                item.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                totalItemsHeight += item.getMeasuredHeight();
            }

            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems -1 );

            ViewGroup.LayoutParams params =
                    listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
            listView.setFocusable(false);

            return true;
        }
        else {
            return false;
        }
    }

    public static Date getDateFromString(String dateString) {
        try {
            Log.d(TAG, "Parsing date string " + dateString);
            return mDateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.d(TAG, "Error parsing date string");
            return null;
        }
    }

    public static DateTime incrementDate(String dateString) {
        Date date = getDateFromString(dateString);
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(1);
        return dateTime;
    }

    public static DateTime decrementDate(String dateString) {
        Date date = getDateFromString(dateString);
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.minusDays(1);
        return dateTime;
    }

}
