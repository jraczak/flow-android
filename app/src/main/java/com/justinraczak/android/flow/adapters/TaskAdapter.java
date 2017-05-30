package com.justinraczak.android.flow.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinraczak.android.flow.R;
import com.justinraczak.android.flow.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Justin on 5/26/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

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
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_task, parent, false);
        return new TaskHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        Task task = this.mTasksList.get(position);
        holder.bindTask(task);
    }

    @Override
    public int getItemCount() {
        return this.mTasksList.size();
    }

    //@Override
    //public int getCount() {
    //    return mCount;
    //}

    //@Override
    //public Object getItem(int position) {
    //    return mTasksList.get(position);
    //}

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Legacy view management from ListView and BaseAdapter
    //@Override
    //public View getView(int position, View convertView, ViewGroup parent) {
//
    //    LinearLayout linearLayout;
    //    TextView taskNameTextView;
//
    //    final Task task = (Task) this.getItem(position);
    //    Log.d(LOG_TAG, "convertView is " + convertView);
    //    Log.d(LOG_TAG, "parent is " + parent);
//
    //    if (convertView == null) {
    //        Log.d(LOG_TAG, "task name is " + task.getName());
    //        linearLayout = (LinearLayout) mInflater.inflate(R.layout.list_item_task, parent, false);
    //    } else {
    //        linearLayout = (LinearLayout) convertView;
    //    }
    //    Log.d(LOG_TAG, "Assigning textview title to task " + task.getName());
    //    taskNameTextView = (TextView) linearLayout.findViewById(R.id.task_view_task_name);
    //    taskNameTextView.setText(task.getName());
//
//
    //    return linearLayout;
    //}
}
