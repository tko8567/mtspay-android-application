/*

03.12.2017, 15:26

id: 121813585969020928
id: 121813585969020929
id: 121813585969020930
id: 121813585969020931
id: 121813585969020932


 */

package com.greentea.mtspayandroidapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Launcher extends Activity {

    private Intent menuIntent;

    private static final int AUTHENTICATION_ACTIVITY_REQUEST_CODE = 0xf0;
    public static final int MENU_ACTIVITY_REQUEST_CODE = 0xf1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        if (Account.getInstance(this).isLoggedIn()) {
            int status = Account.getInstance(this).queryPerson();
            Log.e("Launcher", "status=" + status);

            switch (status) {

                case Account.STATUS_OK:
                    Log.d("Launcher", "queryPerson STATUS_OK, "
                            + Account.getInstance().getPerson().firstName() + " "
                            + Account.getInstance().getPerson().lastName());
                    launchMenu();
                    break;

                case Account.STATUS_RECOVERED:
                    Log.d("Launcher", "queryPerson STATUS_RECOVERED, "
                            + Account.getInstance().getPerson().firstName() + " "
                            + Account.getInstance().getPerson().lastName());
                    launchMenu();
                    break;

                case Account.STATUS_APOLLO_EXCEPTION:
                    Log.d("Launcher", "queryPerson STATUS_APOLLO_EXCEPTION, "
                            + Account.getInstance().getException());
                    AppHelper.showExceptionAlertDialog(Account.getInstance().getException(), this);
                    break;

                case Account.STATUS_NO_ACCOUNT_FOUND:
                    Log.d("Launcher", "queryPerson STATUS_NO_ACCOUNT_FOUND, token="
                            + Account.getInstance().getToken());
                    Log.e("Launcher", "Cleaning Account token...");
                    Account.getInstance().setToken("null");
                    Account.getInstance().saveToken();
                    launchAuthentication();
            }
        } else launchAuthentication();
        Log.wtf("Launcher", "onCreate was finished!?");
        */
launchMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AUTHENTICATION_ACTIVITY_REQUEST_CODE:
                if (resultCode == AuthenticationActivity.RESULT_LOGGED_IN) {
                    launchMenu();
                } else if (resultCode == AuthenticationActivity.RESULT_CANCELED) {
                    finish();
                }
                break;

            case MENU_ACTIVITY_REQUEST_CODE:
                if (resultCode == MenuActivity.KEEP_SESSION) {
                    finish();
                } else {
                    //logout();
                    launchAuthentication();
                }
                break;
            default:
                Log.e("Launcher", "ERROR: DEFAULT ACTIVITY RESULT! requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);
                break;
        }
    }

    private volatile Intent authenticationIntent;

    private void launchAuthentication() {
        authenticationIntent = new Intent(this, AuthenticationActivity.class);
        startActivityForResult(authenticationIntent, AUTHENTICATION_ACTIVITY_REQUEST_CODE);
    }

    private void launchMenu() {
        menuIntent = new Intent(Launcher.this, MenuActivity.class);
        startActivityForResult(menuIntent, MENU_ACTIVITY_REQUEST_CODE);
    }

    public static class PrefConstants {

        public static final String TOKEN = "token";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";

    }


}
