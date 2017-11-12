package com.greentea.mtspayandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.greentea.mtspayandroidapplication.net.graphql.Queries;

import java.util.HashMap;
import java.util.Map;

// TODO: 12.11.2017 Create new thread / Join ApolloClient query thread / continue working

public class AuthenticationActivity extends AppCompatActivity {

    public static final int RESULT_LOGGED_IN = 0x10;
    public static final int RESULT_LOG_IN_FAILED = 0x11;
    public static final int RESULT_CANCELED = 0x20;

    private EditText mLoginEntry;
    private EditText mPasswordEntry;
    private Button mLogin;
    private AlertDialog unauthorizedDialog;
    private AlertDialog exceptionDialog;
    private Intent thisIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        thisIntent = getIntent();
        mLoginEntry = (EditText) findViewById(R.id.loginEntry);
        mPasswordEntry = (EditText) findViewById(R.id.passwordEntry);
        mLogin = (Button) findViewById(R.id.loginButton);
        mLogin.setOnClickListener(onLoginClickListener);

        exceptionDialog = new AlertDialog.Builder(this).create();
    }

    private void showExceptionAlertDialog(Exception e) {
        exceptionDialog.setTitle("Exception: " + e.getClass().getName());
        exceptionDialog.setMessage("Message: " + e.getMessage());
        exceptionDialog.show();
    }


    View.OnClickListener onLoginClickListener = new View.OnClickListener() {

        private String login;
        private String password;
        private volatile Map<String, Object> result = new HashMap<String, Object>(){{
            put("Debug", "WORKING");
        }};

        private Object lock = new Object();

        @Override
        public void onClick(View v) {

            login = mLoginEntry.getText().toString();
            password = mPasswordEntry.getText().toString();

            if (login.equals("") || password.equals("")) {
                if (unauthorizedDialog == null) {
                    unauthorizedDialog = new AlertDialog.Builder(AuthenticationActivity.this).create();
                    unauthorizedDialog.setTitle(R.string.unauthorizedAlertTitle);
                    unauthorizedDialog.setMessage(getString(R.string.unauthorizedAlertMessage));
                }
                unauthorizedDialog.show();
            } else {

                Log.i("AuthenticationActivity", "newThread");

                Queries.loginQuery(login, lock, new Queries.OnQueryPerformedListener() {

                    @Override
                    public void onFinish(boolean succeeded, Map<String, Object> data) {
                        result = data;
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });
/*
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
*/


                try {
                    synchronized (lock) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.w("AuthenticationActivity", result.toString());

                Intent resultIntent = new Intent();
                resultIntent.putExtra(Launcher.PrefConstants.FIRST_NAME, (String) result.get(Launcher.PrefConstants.FIRST_NAME));
                resultIntent.putExtra(Launcher.PrefConstants.LAST_NAME, (String) result.get(Launcher.PrefConstants.LAST_NAME));
                resultIntent.putExtra(Launcher.PrefConstants.TOKEN, login);
                setResult(RESULT_LOGGED_IN, resultIntent);
                finish();

/*
                Map<String, Object> map = performLogin(mLoginEntry.getText().toString());
                if ((Boolean)map.get("succeeded")) {
                    Intent thisIntent = AuthenticationActivity.this.getIntent();
                    thisIntent.putExtra(Launcher.PrefConstants.FIRST_NAME, (String)map.get(Launcher.PrefConstants.FIRST_NAME));
                    thisIntent.putExtra(Launcher.PrefConstants.LAST_NAME, (String)map.get(Launcher.PrefConstants.LAST_NAME));
                    thisIntent.putExtra(Launcher.PrefConstants.TOKEN, mLoginEntry.getText().toString());

                    Toast.makeText(
                            AuthenticationActivity.this,
                            "Logged in: " + map.get("firstName") + " " + map.get("lastName"),
                            Toast.LENGTH_LONG).show();
                    finish();

                }
*/
            }
        }
    };

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
