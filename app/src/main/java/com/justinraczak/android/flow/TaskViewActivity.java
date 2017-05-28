package com.justinraczak.android.flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.justinraczak.android.flow.adapters.TaskAdapter;
import com.justinraczak.android.flow.models.Task;
import com.justinraczak.android.flow.utils.Utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class TaskViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewTaskFragment.OnNewTaskFragmentInteractionListener {

    private static final String LOG_TAG = TaskViewActivity.class.getSimpleName();

    // For querying and displaying the day's tasks
    private ListView mTaskListView;
    private TaskAdapter mTaskAdapter;
    private RealmResults<Task> mTaskRealmResults;
    public Realm mRealm;

    // For navigating the dates
    public TextView mDateHeader;
    public DateTime mCurrentSelectedDate;
    public String mCurrentSelectedDateString;
    public SimpleDateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public ImageButton mPreviousDateButton;
    public ImageButton mNextDateButton;
    public SimpleDateFormat mHeaderDateFormat = new SimpleDateFormat("E, MMM d");

    NewTaskFragment mNewTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Not sure why this is needed here, but was getting errors just having it in the application
        Realm.init(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //        .setAction("Action", null).show();
                showNewTaskDialog();
            }
        });

        //TODO: Figure out how to default the date to today, but don't erase selection if user if navigating from within the app
        mCurrentSelectedDate = new DateTime();
        mCurrentSelectedDateString = mDateFormat.format(mCurrentSelectedDate.toDate());
        Log.d(LOG_TAG, "Setting current date string to " + mCurrentSelectedDateString);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mDateHeader = (TextView) findViewById(R.id.textview_today_header);
        Date date = new Date();
        mDateHeader.setText(mHeaderDateFormat.format(date));

        mPreviousDateButton = (ImageButton) findViewById(R.id.task_view_left_chevron);
        mNextDateButton = (ImageButton) findViewById(R.id.task_view_right_chevron);
        mPreviousDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPreviousDay();
            }
        });
        mNextDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNextDay();
            }
        });

        //TODO: Finish implementing this and add safety checks for null
        //TextView userEmailTextView = (TextView) findViewById(R.id.nav_user_email);
        //userEmailTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //TextView userName = (TextView) findViewById(R.id.textview_user_name);
        //userName.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Fetch the tasks for the current day and load them into the adapter for display in list view
        mRealm = Realm.getDefaultInstance();
        mTaskRealmResults = mRealm.where(Task.class)
                .equalTo("currentScheduledDate", mCurrentSelectedDateString)
                .findAll();
        mTaskListView = (ListView) findViewById(R.id.task_view_tasks_listview);
        mTaskAdapter = new TaskAdapter(this, mTaskRealmResults.size(), mTaskRealmResults);
        mTaskListView.setAdapter(mTaskAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_profile) {
            Intent profileIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
            startActivity(profileIntent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNewTaskDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewTaskFragment newTaskFragment = NewTaskFragment.newInstance();
        mNewTaskFragment = newTaskFragment;
        newTaskFragment.show(fragmentManager, "fragment_new_task");
    }

    @Override
    public void onSaveButtonPressed(String taskName) {
        Log.d(LOG_TAG, "New task save button pressed");
        Log.d(LOG_TAG, "Received new task with name " + taskName);
        getSupportFragmentManager().beginTransaction().remove(mNewTaskFragment).commit();
        mNewTaskFragment = null;
        Toast.makeText(getApplicationContext(), "Created " + taskName + " task", Toast.LENGTH_SHORT).show();
        Task task = new Task(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                Task.getNewAutoIncrementId(),
                taskName,
                null,
                mCurrentSelectedDateString);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Log.d(LOG_TAG, "Copying task " + task + " to Realm");
        realm.copyToRealm(task);
        realm.commitTransaction();
        updateTaskList();
    }

    public void navigateToPreviousDay() {
        Log.d(LOG_TAG, "Attempting to navigate from " + mCurrentSelectedDateString);
        mCurrentSelectedDate = Utils.decrementDate(mCurrentSelectedDateString);
        mCurrentSelectedDateString = mDateFormat.format(mCurrentSelectedDate.toDate());
        Log.d(LOG_TAG, "Updated date view to " + mCurrentSelectedDateString);
        mDateHeader.setText(mHeaderDateFormat.format(mCurrentSelectedDate.toDate()));
        updateTaskList();
    }

    public void navigateToNextDay() {
        Log.d(LOG_TAG, "Attempting to navigate from " + mCurrentSelectedDateString);
        mCurrentSelectedDate = Utils.incrementDate(mCurrentSelectedDateString);
        mCurrentSelectedDateString = mDateFormat.format(mCurrentSelectedDate.toDate());
        Log.d(LOG_TAG, "Updated date view to " + mCurrentSelectedDateString);
        mDateHeader.setText(mHeaderDateFormat.format(mCurrentSelectedDate.toDate()));
        updateTaskList();
    }

    public void updateTaskList() {
        Log.d(LOG_TAG, "Updating task list");
        mTaskRealmResults = mRealm.where(Task.class)
                .equalTo("currentScheduledDate", mCurrentSelectedDateString)
                .findAll();
        mTaskAdapter = new TaskAdapter(this, mTaskRealmResults.size(), mTaskRealmResults);
        mTaskListView.setAdapter(mTaskAdapter);
        Utils.setListViewHeightBasedOnItems(mTaskListView);
    }
}
