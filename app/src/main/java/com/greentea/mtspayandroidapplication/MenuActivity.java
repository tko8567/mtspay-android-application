package com.greentea.mtspayandroidapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    public static final int KEEP_SESSION = 0;
    public static final int LOGOUT = 1;

    private TextView mUsername;
    private ImageButton mLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String firstName = getIntent().getStringExtra(Launcher.PrefConstants.FIRST_NAME);
        String lastName = getIntent().getStringExtra(Launcher.PrefConstants.LAST_NAME);

        mUsername = (TextView) findViewById(R.id.menu_username);
        mUsername.setText(
                firstName + " " + lastName
        );
        mLogout = (ImageButton) findViewById(R.id.logout_button);
    }


    public void onLogout(View v) {
        setResult(LOGOUT);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(KEEP_SESSION);
    }
}
