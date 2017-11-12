package com.greentea.mtspayandroidapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class Launcher extends Activity {

    public static final String USERDATA_PREFS_NAME = "userdata";

    private SharedPreferences sharedPreferences;
    private String token;
    private String firstName;
    private String lastName;
    private FindPersonByIdQuery.Person person;
    private Intent menuIntent;

    private static final int AUTHENTICATION_ACTIVITY_REQUEST_CODE = 0xf0;
    public static final int MENU_ACTIVITY_REQUEST_CODE = 0xf1;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        if (sharedPreferences == null) sharedPreferences = getSharedPreferences(USERDATA_PREFS_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(PrefConstants.TOKEN, null);
        firstName = sharedPreferences.getString(PrefConstants.FIRST_NAME, null);
        lastName = sharedPreferences.getString(PrefConstants.LAST_NAME, null);

        if (token == null || firstName == null || lastName == null){
            this.launchAuthentication();
        } else {
            launchMenu();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Launcher", "onActivityResult#requestCode=" + requestCode);
        switch (requestCode) {
            case AUTHENTICATION_ACTIVITY_REQUEST_CODE:
                if (resultCode == AuthenticationActivity.RESULT_LOGGED_IN) {
                    try {
                        String firstName = data.getStringExtra(PrefConstants.FIRST_NAME);
                        String lastName = data.getStringExtra(PrefConstants.LAST_NAME);
                        String token = data.getStringExtra(PrefConstants.TOKEN);
                        Log.d("Launcher", "firstName=" + firstName + ", lastName=" + lastName + ", token=" + token);
                        login(firstName, lastName, token);
                        launchMenu();

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        launchAuthentication();
                    }
                } else if (resultCode == AuthenticationActivity.RESULT_CANCELED) {
                    finish();
                }
                break;

            case MENU_ACTIVITY_REQUEST_CODE:
                if (resultCode == MenuActivity.KEEP_SESSION) {
                    finish();
                } else {
                    logout();
                    launchAuthentication();
                }
        }
    }

    private volatile Intent authenticationIntent;

    private void launchAuthentication() {
        authenticationIntent = new Intent(this, AuthenticationActivity.class);
        startActivityForResult(authenticationIntent, AUTHENTICATION_ACTIVITY_REQUEST_CODE);
    }

    private void login(String firstName, String lastName, String token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        refreshPrefs();

    }

    private void launchMenu() {
        menuIntent = new Intent(Launcher.this, MenuActivity.class);
        menuIntent.putExtra(PrefConstants.FIRST_NAME, sharedPreferences.getString(PrefConstants.FIRST_NAME, "null"));
        menuIntent.putExtra(PrefConstants.LAST_NAME, sharedPreferences.getString(PrefConstants.LAST_NAME, "null"));
        startActivityForResult(menuIntent, MENU_ACTIVITY_REQUEST_CODE);
    }

    private void refreshPrefs() {
        sharedPreferences.edit()
                .putString(PrefConstants.TOKEN, token)
                .putString(PrefConstants.FIRST_NAME, firstName)
                .putString(PrefConstants.LAST_NAME, lastName)
                .apply();
    }

    public void logout() {
        this.token = null;
        this.firstName = null;
        this.lastName = null;
        refreshPrefs();
    }

    public static class PrefConstants {

        public static final String TOKEN = "token";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";

    }


}
