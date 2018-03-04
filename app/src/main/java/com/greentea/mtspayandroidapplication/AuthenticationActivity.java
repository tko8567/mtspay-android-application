package com.greentea.mtspayandroidapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greentea.mtspayandroidapplication.util.Account;
import com.greentea.mtspayandroidapplication.util.AppHelper;

import java.util.Map;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "AuthenticationActivity";

    public static final int RESULT_LOGGED_IN = 0x10;
    public static final int RESULT_LOG_IN_FAILED = 0x11;
    public static final int RESULT_CANCELED = 0x20;

    private EditText mLoginEntry;
    private EditText mPasswordEntry;
    private Button mLogin;
    private AlertDialog unauthorizedDialog;
    private AlertDialog networkUnavailableDialog;
    private AlertDialog incorrectFieldsDialog;

    private ConnectivityManager cm;
    private NetworkInfo ni;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mLoginEntry = (EditText) findViewById(R.id.loginEntry);
        mPasswordEntry = (EditText) findViewById(R.id.passwordEntry);
        mLogin = (Button) findViewById(R.id.loginButton);
        mLogin.setOnClickListener(onLoginClickListener);

        cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private View.OnClickListener onLoginClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            v.setEnabled(false);

            String login = mLoginEntry.getText().toString();
            String password = mPasswordEntry.getText().toString();

            ni = cm.getActiveNetworkInfo();

            if (login.equals("") || password.equals("") ) {
                showIncorrectFieldsAlertDialog();
                return;
            }

            if (ni != null) {
                if (ni.isConnected()) {
                    // TODO: 28.11.2017 perform queryPerson here
                    Map<String, Object> map = Account.getInstance(AuthenticationActivity.this).login(login, password);

                    Account.ResponseStatus i = (Account.ResponseStatus) map.get(Account.STATUS);
                    switch (i) {
                        case SUCCESS:
                            Account.getInstance().saveToken();
                            setResult(RESULT_LOGGED_IN);
                            v.setEnabled(true);
                            finish();
                            break;

                        case WRONG_PAIR:
                            Log.i(TAG, "WRONG_PAIR");
                            showUnauthorizedDialog();
                            break;

                        case FAILURE:
                            Log.wtf(TAG, "FAILURE SECTION?: " + map.get(Account.EXCEPTION).toString());
                            break;

                        case UNKNOWN_FAILURE:
                            Log.wtf(TAG, "Unknown failure in login: " + map.get(Account.EXCEPTION).toString());
                            break;

                        case HTTP_ERROR:

                            break;

                        default:
                            AppHelper.showExceptionAlertDialog(
                                    (Exception) map.get(Account.EXCEPTION),
                                    AuthenticationActivity.this
                            );
                            break;
                    }

                    /*

            int status = Account.getInstance(this).queryPerson();
            Log.e("Launcher", "status=" + status);

            switch (status) {

                case Account.STATUS_OK:
                    Log.d("Launcher", "queryPerson STATUS_OK, "
                            + person.firstName + " "
                            + person.lastName);
                    launchMenu();
                    break;

                case Account.STATUS_RECOVERED:
                    Log.d("Launcher", "queryPerson STATUS_RECOVERED, "
                            + person.firstName + " "
                            + person.lastName);
                    launchMenu();
                    break;

                case Account.STATUS_APOLLO_EXCEPTION:
                    Log.d("Launcher", "queryPerson STATUS_APOLLO_EXCEPTION, "
                            + Account.getInstance().getException());
                    WindowHelper.showExceptionAlertDialog(Account.getInstance().getException(), this);
                    break;

                case Account.STATUS_NO_ACCOUNT_FOUND:
                    Log.d("Launcher", "queryPerson STATUS_NO_ACCOUNT_FOUND, token="
                            + Account.getInstance().getToken());
                    Log.e("Launcher", "Cleaning Account token...");
                    Account.getInstance().setToken("null");
                    Account.getInstance().saveToken();
                    launchAuthentication();
            }

                     */

                } else {
                    // TODO: 28.11.2017 no connection (e.g. wifi with no ap accessed)
                    Toast.makeText(AuthenticationActivity.this, "Check internet connection!", Toast.LENGTH_LONG).show();
                }
            } else {
                // TODO: 28.11.2017 no active network - wifi and data transfer are offline
                Toast.makeText(AuthenticationActivity.this, "Enable network to perform this action", Toast.LENGTH_LONG).show();

            }
            /*
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.isConnected()) {

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

                        Queries.OnQueryPerformedListener listener = new Queries.OnQueryPerformedListener() {

                            @Override
                            public void onFinish(boolean succeeded, Map<String, Object> data) {
                                result = (Person) data.get(Queries.Constants.PERSON);
                                synchronized (lock) {
                                    lock.notify();
                                }
                            }
                        };

                        Queries.loginQuery(login, listener);

                        try {
                            synchronized (lock) {
                                if (Queries.isLastQueryPerformed()) lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Log.w("AuthenticationActivity", result.toString());

                        if (result != null) {

                            Intent resultIntent = new Intent();
                            // TODO: 24.11.2017 login person
                            setResult(RESULT_LOGGED_IN, resultIntent);
                            finish();

                        } else {
                            if (unauthorizedDialog == null) {
                                unauthorizedDialog = new AlertDialog.Builder(AuthenticationActivity.this).create();
                                unauthorizedDialog.setTitle(R.string.unauthorizedAlertTitle);
                                unauthorizedDialog.setMessage(getString(R.string.unauthorizedAlertMessage));
                            }
                        }
                    }
                }
            } else {
                if (networkUnavailableDialog == null) {
                    networkUnavailableDialog = new AlertDialog.Builder(AuthenticationActivity.this).create();
                    networkUnavailableDialog.setTitle(R.string.networkUnavailableTitle);
                    networkUnavailableDialog.setMessage(getString(R.string.networkUnavailableMessage));
                }
                networkUnavailableDialog.show();
            }*/
            v.setEnabled(true);
        }
    };

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    private void showIncorrectFieldsAlertDialog() {
        if (incorrectFieldsDialog == null) {
            incorrectFieldsDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.incorrectFieldsTitle)
                    .setMessage(R.string.incorrectFieldsMessage)
                    .create();
        }
        incorrectFieldsDialog.show();
    }

    private void showUnauthorizedDialog() {
        if (unauthorizedDialog == null) {
            unauthorizedDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.unauthorizedAlertTitle)
                    .setMessage(R.string.unauthorizedAlertMessage)
                    .create();
        }
        unauthorizedDialog.show();
    }
}
