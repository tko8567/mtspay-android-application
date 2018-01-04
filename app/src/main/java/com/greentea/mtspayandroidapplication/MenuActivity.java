package com.greentea.mtspayandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.greentea.mtspayandroidapplication.util.Account;

import java.text.DecimalFormat;

public class MenuActivity extends AppCompatActivity {

    public static final int KEEP_SESSION = 0;
    public static final int LOGOUT = 1;

    private TextView mUsername;
    private TextView mBalance;
    private ImageButton mLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String firstName = Account.getInstance().getPerson().firstName();
        String lastName = Account.getInstance().getPerson().lastName();
        String balance;
        DecimalFormat df = new DecimalFormat(getString(R.string.moneyFormat));
        balance = df.format(Account.getInstance().getDefaultCard().balance());

        mUsername = (TextView) findViewById(R.id.menu_username);
        mBalance = (TextView) findViewById(R.id.menu_balance);
        mLogout = (ImageButton) findViewById(R.id.logout_button);

        mUsername.setText(firstName + " " + lastName);
        mBalance.setText(balance);
    }

    public void onLogout(View v) {
        setResult(LOGOUT);
        finish();
    }

    public void onPersonalAccountClick(View v) {
        Intent intent = new Intent(this, PersonalAccountActivity.class);
        startActivity(intent);
    }

    public void onReadQrClick(View v) {

    }

    public void onTransactionsClick(View v) {
        Intent intent = new Intent(this, TransactionsActivity.class);
        startActivity(intent);
    }

    public void onTechnicalSupportClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(KEEP_SESSION);
    }
}