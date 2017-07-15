package com.justinraczak.android.flow;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    EditText mNameEditText;
    EditText mEmailEditText;
    EditText mPasswordEditText;
    Button mSignUpButton;
    TextView mSignInLink;

    String mEmail;
    String mPassword;
    String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNameEditText = (EditText) findViewById(R.id.signup_name_edittext);
        mEmailEditText = (EditText) findViewById(R.id.signup_email_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.signup_password_edittext);
        mSignUpButton = (Button) findViewById(R.id.button_sign_up);
        mSignInLink = (TextView) findViewById(R.id.signup_sign_in_link);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(getApplicationContext(), LoginActivity.class);
                //TODO: Send the form field values through in case the user has already filled them in
                startActivity(signInIntent);
            }
        });

    }

    public void createAccount() {
        Log.d(LOG_TAG, "Attempting to create account");

        //Set the field values so they're available in the background thread
        mEmail = mEmailEditText.getText().toString();
        mName = mNameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();

        //Fire the background task to create account via the API
        CreateAccountAsyncTask createAccountAsyncTask = new CreateAccountAsyncTask();
        createAccountAsyncTask.execute();
    }

    public boolean validateFields() {
        //TODO: Do this
        return false;
    }

    private class CreateAccountAsyncTask extends AsyncTask<Void, Void, String> {

        HttpsURLConnection httpsURLConnection;
        URL url = null;

        @Override
        protected String doInBackground(Void... params) {

            String jsonResponseString;

            try {
                url = new URL("https://pure-caverns-40977.herokuapp.com/api/auth");
            } catch (MalformedURLException e) {
                Log.d(LOG_TAG, e.toString());
                return e.toString();
            }

            try {
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
            } catch (IOException e) {
                Log.d(LOG_TAG, e.toString());
                return e.toString();
            }

            httpsURLConnection.setRequestProperty("email", mEmail);
            httpsURLConnection.setRequestProperty("password", mPassword);
            httpsURLConnection.setRequestProperty("password_confirmation", mPassword);
            httpsURLConnection.setRequestProperty("name", mName);

            try {
                int responseCode = httpsURLConnection.getResponseCode();
                Log.d(LOG_TAG, "HTTP response code is " + responseCode);

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    // TODO: Create the user in the local database and create a session to sign them in

                    Map<String, List<String>> responseHeader = httpsURLConnection.getHeaderFields();
                    Log.d(LOG_TAG, "Header fields: " + responseHeader);
                    for (Map.Entry<String, List<String>> entry : responseHeader.entrySet()) {
                        Log.d(LOG_TAG, entry.getKey() + " : " + entry.getValue());
                    }

                    InputStream responseBody = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseBody));

                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                        Log.d(LOG_TAG, "Read line was: " + line);
                    }
                    jsonResponseString = result.toString();
                    Log.d(LOG_TAG, "JSON response string is: " + jsonResponseString);
                    return jsonResponseString;
                } else {
                    return "Failed to reach the API and create account";
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, e.toString());
                return e.toString();
            } finally {
                httpsURLConnection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.signup_sign_in_link);
            textView.setText(result);
        }
    }
}
