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

import com.greentea.mtspayandroidapplication.util.Account;

public class Launcher extends Activity {

    public static final String TAG = "Launcher";
    
    private Intent menuIntent;

    private static final int AUTHENTICATION_ACTIVITY_REQUEST_CODE = 0xf0;
    public static final int MENU_ACTIVITY_REQUEST_CODE = 0xf1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Account acc = Account.getInstance(this);
        if (!acc.isLoggedIn()) {
            launchAuthentication();
        } else {

            launchMenu();
        }



//        if (Account.getInstance(this).isLoggedIn()) {
//            Map<String, Object> map = Account.getInstance(this).login();
//            Log.e(TAG, "map=" + map);
//
//            switch (map) {
//
//                case Account.STATUS_OK:
//                    Log.d(TAG, "queryPerson STATUS_OK, "
//                            + Account.getInstance().getPerson().firstName() + " "
//                            + Account.getInstance().getPerson().lastName());
//                    launchMenu();
//                    break;
//
//                case Account.STATUS_RECOVERED:
//                    Log.d(TAG, "queryPerson STATUS_RECOVERED, "
//                            + Account.getInstance().getPerson().firstName() + " "
//                            + Account.getInstance().getPerson().lastName());
//                    launchMenu();
//                    break;
//
//                case Account.STATUS_APOLLO_EXCEPTION:
//                    Log.d(TAG, "queryPerson STATUS_APOLLO_EXCEPTION, "
//                            + Account.getInstance().getException());
//                    AppHelper.showExceptionAlertDialog(Account.getInstance().getException(), this);
//                    break;
//
//                case Account.STATUS_NO_ACCOUNT_FOUND:
//                    Log.d(TAG, "queryPerson STATUS_NO_ACCOUNT_FOUND, token="
//                            + Account.getInstance().getToken());
//                    Log.e(TAG, "Cleaning Account token...");
//                    Account.getInstance().setToken("null");
//                    Account.getInstance().saveToken();
//                    launchAuthentication();
//            }
//        } else {
//            Log.i(TAG, "Already logged in with token=" + Account.getInstance().getToken());
//            launchAuthentication();
//        }
//        Log.wtf(TAG, "onCreate was finished!?");
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
                    Account.getInstance(this).deleteToken();
                    launchAuthentication();
                }
                break;
            default:
                Log.e(TAG, "ERROR: DEFAULT ACTIVITY RESULT! requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);
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
