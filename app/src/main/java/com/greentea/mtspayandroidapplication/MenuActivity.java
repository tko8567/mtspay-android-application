package com.greentea.mtspayandroidapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.greentea.mtspayandroidapplication.util.Account;
import com.greentea.mtspayandroidapplication.util.AppHelper;

public class MenuActivity extends AppCompatActivity {

    public static final int KEEP_SESSION = 0;
    public static final int LOGOUT = 1;
    private static final String TAG = "Launcher";

    private TextView mUsername;
    private TextView mBalance;
    private ImageButton mLogout;

    /* activity overriders */

    // lifecycle overriders

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String firstName = "null";
        String lastName = "null";
        String defaultWalletName = "null";
        Account acc = Account.getInstance(this);

        try {
            firstName = acc.getPerson().firstName();
            lastName = acc.getPerson().lastName();
            defaultWalletName = Account.getInstance().getDefaultWallet().name();
        } catch (NullPointerException e) {
            Log.e(TAG, "Account instance is null in onCreate");
        }

        mUsername = (TextView) findViewById(R.id.menu_username);
        mBalance = (TextView) findViewById(R.id.menu_balance);
        mLogout = (ImageButton) findViewById(R.id.logout_button);

        mUsername.setText(String.format("%s %s", firstName, lastName));
        mBalance.setText(String.format("Main wallet: %s", defaultWalletName));
    }


    // other overriders

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(KEEP_SESSION);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu
                .add("Exit")
                .setIcon(R.mipmap.exit)
                .setOnMenuItemClickListener(
                        item -> {
                            setResult(LOGOUT);
                            finish();
                            return true;
                        }
                );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA:

                if (grantResults.length > 0) {
                    if (grantResults[0] == (PackageManager.PERMISSION_GRANTED)) {
                        // granted
                        try {
                            Intent intent = new Intent(this, CameraQrReaderActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            AppHelper.showExceptionAlertDialog(e, this);
                        }
                    } else {
                        // not granted
                    }
                } else {
                    // cancelled
                }

                break;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /* menu clicks */

    public void onReadQrClick(View v) {
        if (checkCameraHardware()) {}
            /*
            try {
                Intent intent = new Intent(this, CameraQrReaderActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                AppHelper.showExceptionAlertDialog(e, this);
            }
            */
        else {
            Dialog noCameraDialog = new Dialog(this);
            noCameraDialog.setTitle("No camera detected");
            noCameraDialog.setContentView(
                    new android.support.v7.widget.AppCompatTextView(this){{
                        setText("This device has no available cameras");
                    }}
            );
        }
    }

    public void onPersonalAccountClick(View v) {
        Intent intent = new Intent(this, PersonalAccountActivity.class);
        startActivity(intent);
    }

    public void onTransactionsClick(View v) {
        Intent intent = new Intent(this, TransactionsActivity.class);
        startActivity(intent);
    }

    public void onTechnicalSupportClick(View v) {
        Intent intent = new Intent(this, TechnicalSupportActivity.class);
        startActivity(intent);
    }

    public void onLogout(View v) {
        setResult(LOGOUT);
        finish();
    }



    /* internal */

    private static final int PERMISSION_CAMERA = 0;

    private boolean checkCameraHardware() {
        String permission[] = {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permission, PERMISSION_CAMERA);
        return (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
    }

}