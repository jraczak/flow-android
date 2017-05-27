package com.justinraczak.android.flow.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justinraczak.android.flow.R;
import com.justinraczak.android.flow.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Justin on 5/26/17.
 */

public class TaskAdapter extends BaseAdapter {

    private final static String LOG_TAG = TaskAdapter.class.getSimpleName();

    private Realm mRealm;

    private Context mContext;
    public int mCount;
    public RealmResults<Task> mTasksList;
    public LayoutInflater mInflater;

    public TaskAdapter(Context context, int count, RealmResults<Task> tasks) {
        mContext = context;
        mCount = count;
        mTasksList = tasks;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return mTasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout linearLayout;
        TextView taskNameTextView;

        final Task task = (Task) this.getItem(position);
        Log.d(LOG_TAG, "convertView is " + convertView);
        Log.d(LOG_TAG, "parent is " + parent);

        if (convertView == null) {
            Log.d(LOG_TAG, "task name is " + task.getName());
            linearLayout = (LinearLayout) mInflater.inflate(R.layout.list_item_task, parent, false);
        } else {
            linearLayout = (LinearLayout) convertView;
        }
        Log.d(LOG_TAG, "Assigning textview title to task " + task.getName());
        taskNameTextView = (TextView) linearLayout.findViewById(R.id.task_view_task_name);
        taskNameTextView.setText(task.getName());


        return linearLayout;
    }
}
