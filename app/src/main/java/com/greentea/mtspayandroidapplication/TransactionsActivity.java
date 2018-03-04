package com.greentea.mtspayandroidapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greentea.mtspayandroidapplication.util.Account;

import fragment.PersonData.Transaction;

public class TransactionsActivity extends AppCompatActivity {

    private TextView mTransactionsHeader;
    private LinearLayout mTransactionsContainer;
    private AlertDialog mTransactionInfoDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        mTransactionsContainer = (LinearLayout) findViewById(R.id.transactions_container);
        mTransactionsHeader = (TextView) findViewById(R.id.numberOfTransactions);
        mTransactionsHeader.setText(getString(R.string.transactions) + ": "
                + Account.getInstance().getPerson().transactions().size());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        for (Transaction transaction : Account.getInstance().getPerson().transactions()) {
            mTransactionsContainer.addView(createViewForTransaction(transaction));
            mTransactionsContainer.invalidate();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View createViewForTransaction(Transaction transaction) {
/*
        DecimalFormat format = new DecimalFormat(getString(R.string.moneyFormat));

        String name;
        String amount = format.format(transaction.amount());
        boolean fromMeToYou = (transaction.from().id().equals(Account.getInstance().getPerson().id()));

        if (fromMeToYou) {
            if (transaction.to().__typename().equals("Person")) {
                name = transaction.to().asPerson().lastName() + ", " + transaction.to().asPerson().firstName();
            } else {
                name = transaction.to().asOrganization().name();
            }
        } else {
            name = transaction.from().lastName() + ", " + transaction.from().firstName();
        }

        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                                       ViewGroup.LayoutParams.MATCH_PARENT,
                                       ViewGroup.LayoutParams.WRAP_CONTENT) {{
                                   setMargins(
                                           getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                           getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                           getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                           getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                               }}
        );
        layout.setBackgroundResource(R.drawable.red_frame);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setWeightSum(1.0f);

        ImageView arrow = new ImageView(this);
        if (fromMeToYou)
            arrow.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.right_arrow));
        else arrow.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.left_arrow));
        arrow.setLayoutParams(new LinearLayout.LayoutParams(
                                      getResources().getDimensionPixelSize(R.dimen.arrowIconSize),
                                      getResources().getDimensionPixelSize(R.dimen.arrowIconSize)) {{
                                  setMargins(
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                              }}
        );

        TextView vName = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
        vName.setLayoutParams(new LinearLayout.LayoutParams(
                                      ViewGroup.LayoutParams.WRAP_CONTENT,
                                      ViewGroup.LayoutParams.WRAP_CONTENT) {{
                                  setMargins(
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                          getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                                  weight = 0.75f;
                              }}
        );
        vName.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        vName.setText(name);

        TextView vAmount = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
        vAmount.setLayoutParams(new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT) {{
                                    setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                                    weight = 0.2f;
                                }}
        );
        vAmount.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        vAmount.setText(amount);

        layout.addView(arrow);
        layout.addView(vName);
        layout.addView(vAmount);
        layout.setOnClickListener(v -> {
            showTransactionInfo(transaction);
            Log.w("TransactionsActivity", "On click");
        });

        return layout;
    }

    private void showTransactionInfo(Transaction transaction) {
        if (mTransactionInfoDialog == null) {
            mTransactionInfoDialog = new AlertDialog.Builder(this).create();
        }
        mTransactionInfoDialog.setTitle(getString(R.string.transaction) + " #" + transaction.id());

        String from;
        String to;
        String amount;
        String status;
        String description;

        from = transaction.from().lastName() + ", " + transaction.from().firstName();

        to = (transaction.to().__typename().equals("Person")
                ? transaction.to().asPerson().lastName() + ", " + transaction.to().asPerson().firstName()
                : transaction.to().asOrganization().name());

        DecimalFormat format = new DecimalFormat(getString(R.string.moneyFormat));
        amount = format.format(transaction.amount());

        status = transaction.status().name();

        if (transaction.description() == null) {
            description = null;
        } else if (transaction.description().equals("")) {
            description = null;
        } else {
            description = transaction.description();
        }

        mTransactionInfoDialog.setMessage(
                getString(R.string.from) + from + "\n"
                        + getString(R.string.to) + to + "\n"
                        + getString(R.string.amount) + amount + "\n"
                        + getString(R.string.status) + status + "\n"
                        + ((description != null) ? getString(R.string.description) + description : "")
        );
        mTransactionInfoDialog.show();
        */
return null;
    }
}
