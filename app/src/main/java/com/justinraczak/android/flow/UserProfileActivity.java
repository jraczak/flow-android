package com.justinraczak.android.flow;


import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.justinraczak.android.flow.data.UserContract;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = UserProfileActivity.class.getSimpleName();

    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;

    String[] mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView navNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        TextView navEmailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);

        if (mCurrentUser != null) {
            mUserId = new String[]{mCurrentUser.getUid()};
            //Cursor cursor = getContentResolver().query(
            //        UserContract.UserEntry.CONTENT_URI,
            //        null,
            //        UserContract.UserEntry.COLUMN_USER_ID + "=?",
            //        mUserId,
            //        null,
            //        null
            //);
            //DatabaseUtils.dumpCursor(cursor);
            //cursor.moveToFirst();
            //String email = cursor.getString(2);
            //String memberSince = cursor.getString(cursor.getColumnIndex("signupDate"));
            //Log.d(LOG_TAG, "Used cursor to find user with email " + email);
            //cursor.close();

            navNameTextView.setText("");
            navEmailTextView.setText(mCurrentUser.getEmail());
            navigationView.setNavigationItemSelectedListener(this);

            //TextView emailTextView = (TextView) findViewById(R.id.profile_user_email);
            //emailTextView.setText(email);
            //TextView memberSinceTextView = (TextView) findViewById(R.id.profile_member_since);
            //memberSinceTextView.setText(memberSince);

            getSupportLoaderManager().initLoader(0, null, this);

        }
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
        getMenuInflater().inflate(R.menu.user_profile, menu);
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

        if (id == R.id.nav_tasks) {
           Intent intent = new Intent(this, TaskViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_profile) {
            // Do nothing, we're already here

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                UserContract.UserEntry.CONTENT_URI,
                null,
                UserContract.UserEntry.COLUMN_USER_ID + "=?",
                mUserId,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Use the cursor we got to fill in the text views

        DatabaseUtils.dumpCursor(data);
        data.moveToFirst();
        TextView emailTextView = (TextView) findViewById(R.id.profile_user_email);
        emailTextView.setText(data.getString(data.getColumnIndex("email")));
        TextView memberSinceTextView = (TextView) findViewById(R.id.profile_member_since);
        memberSinceTextView.setText(data.getString(data.getColumnIndex("signupDate")));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
