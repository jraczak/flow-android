package com.justinraczak.android.flow.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.justinraczak.android.flow.R;
import com.justinraczak.android.flow.models.Task;

/**
 * Created by Justin on 5/29/17.
 */

public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final static String LOG_TAG = TaskHolder.class.getSimpleName();

    private final TextView taskNameTextView;
    private final CheckBox checkBox;

    private Task task;
    private Context context;

    public TaskHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;
        this.taskNameTextView = (TextView) itemView.findViewById(R.id.task_view_task_name);
        this.checkBox = (CheckBox) itemView.findViewById(R.id.task_view_checkbox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Task " + task.name + " complete state is currently " + task.complete + "; toggling");
                task.toggleCompleteState();
                Log.d(LOG_TAG, "Task complete state is + " + task.complete + " after toggle");
                setUiCompleteState();
                //if (task.complete) {
                //    taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                //} else {
                //    taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                //}
            }
        });

        itemView.setOnClickListener(this);
    }

    public void bindTask(Task task) {
        this.task = task;
        this.taskNameTextView.setText(task.name);
    }

    @Override
    public void onClick(View v) {
        if (this.task != null) {
            Toast.makeText(this.context, "Tapped " + task.name + "task", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUiCompleteState() {
        if (task.complete) {
            checkBox.setChecked(true);
            taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            checkBox.setChecked(false);
            taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
