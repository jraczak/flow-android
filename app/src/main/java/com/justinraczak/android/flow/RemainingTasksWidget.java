package com.justinraczak.android.flow;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.justinraczak.android.flow.models.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

/**
 * Implementation of App Widget functionality.
 */
public class RemainingTasksWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String currentDateString = dateFormat.format(new Date());
        Log.d("Widget", "Set current date string to " + currentDateString);
        Realm realm = Realm.getDefaultInstance();
        int remainingTasks = realm.where(Task.class)
                .equalTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .equalTo("currentScheduledDate",currentDateString)
                .equalTo("complete", false)
                .findAll()
                .size();
        Log.d("Widget", "Found " + remainingTasks + " incomplete tasks for current date");

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.remaining_tasks_widget);
        views.setTextViewText(R.id.widget_task_count, String.valueOf(remainingTasks));

        if (remainingTasks == 0) {
            views.setTextViewText(R.id.widget_label_textview, context.getString(R.string.appwidget_no_tasks_text));
        } else {
            views.setTextViewText(R.id.widget_label_textview, widgetText);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

