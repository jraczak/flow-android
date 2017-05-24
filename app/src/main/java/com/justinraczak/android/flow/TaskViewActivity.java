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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TaskViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewTaskFragment.OnNewTaskFragmentInteractionListener {

    private static final String LOG_TAG = TaskViewActivity.class.getSimpleName();

    NewTaskFragment mNewTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //        .setAction("Action", null).show();
                showNewTaskDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //TODO: Finish implementing this and add safety checks for null
        //TextView userEmailTextView = (TextView) findViewById(R.id.nav_user_email);
        //userEmailTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //TextView userName = (TextView) findViewById(R.id.textview_user_name);
        //userName.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        //TODO: Implement saving the task
        //TODO: Implement refreshing the task list
    }
}
