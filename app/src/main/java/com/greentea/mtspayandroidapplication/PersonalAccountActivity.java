
package com.greentea.mtspayandroidapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greentea.mtspayandroidapplication.util.Account;

import java.util.List;

import fragment.PersonData;

import static fragment.PersonData.Wallet;

@SuppressWarnings("FieldCanBeLocal")
public class PersonalAccountActivity extends AppCompatActivity {

    // STATIC SECTION IS TARGETED TO INITIALIZE AN ACTIVITY WITH TEST DATA
    // TODO: 18.12.2017 Delete static section after finishing the testing

    private TextView mFullName;
    private LinearLayout mCardsLayout;
    private String firstName = Account.getInstance().getPerson().firstName();
    private String lastName = Account.getInstance().getPerson().lastName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PersonalAccountActivity", "onCreate performed");
        setContentView(R.layout.personal_account_activity);

        mFullName = (TextView) findViewById(R.id.fullName);
        mCardsLayout = (LinearLayout) findViewById(R.id.cards_layout);
        mFullName.setText(lastName + ", " + firstName);

        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        List<PersonData.Wallet> wallets = Account.getInstance().getPerson().wallets();
        for (int i = 0; i < wallets.size(); i++) {
                mCardsLayout.addView(createViewForWallet(wallets.get(i), i));
        }
        mCardsLayout.invalidate();
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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    private static final int idPrefix = 0x7d000000;

//    private View createViewForWallet(Wallet wallet, int queue) {
//        // What to show ?
//        return null;
//    }


    @SuppressWarnings("ConstantConditions")
    private View createViewForWallet(Wallet wallet, int queue) {

//        StringBuilder codeBuilder = new StringBuilder("xxxx ");
//        for (int i = 0; i < 4; i++)
//            codeBuilder.append(wallet.number().charAt(wallet.number().length() - 4 + i));
//
//        DecimalFormat format = new DecimalFormat(getString(R.string.moneyFormat));
//
//        // TODO: 18.12.2017 Update payment system support once server gets one
//        String paymentSystem = "VISA";//wallet.paymentSystem;
//        String code = codeBuilder.toString();
//        String validThru = getString(R.string.validThru) + wallet.validThru();
//        String balance = format.format(wallet.balance());
//        boolean isDefault = wallet.isDefault();
//
//        RelativeLayout layout = new RelativeLayout(this);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        ){{ setMargins(
//                getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                getResources().getDimensionPixelSize(R.dimen.defaultMargin));
//        }});
//        layout.setBackgroundResource(R.drawable.red_frame);
//
//        final TextView vPaymentSystem = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
//        vPaymentSystem.setLayoutParams(
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                ) {{
//                    setMargins(
//                            getResources().getDimensionPixelSize(R.dimen.increasedMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
//                }}
//        );
//        if (isDefault) vPaymentSystem.setTextColor(getResources().getInteger(R.color.colorAccent));
//        vPaymentSystem.setLabelFor(idPrefix + queue * 3);
//        vPaymentSystem.setGravity(Gravity.LEFT|Gravity.TOP);
//        vPaymentSystem.setText(paymentSystem);
//
//        TextView vBalance = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
//        vBalance.setLayoutParams(
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                ) {{
//                    setMargins(
//                            getResources().getDimensionPixelSize(R.dimen.increasedMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
//                }}
//        );
//        vBalance.setLabelFor(idPrefix + queue * 3 + 1);
//        vBalance.setGravity(Gravity.RIGHT|Gravity.TOP);
//        vBalance.setText(balance);
//
//
//        TextView vCode = new TextView(new ContextThemeWrapper(this, R.style.SubText));
//        vCode.setLayoutParams(
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                ) {{
//                    setMargins(
//                            getResources().getDimensionPixelSize(R.dimen.increasedMargin),
//                            0,
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
//                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
//                    addRule(BELOW, vPaymentSystem.getId());
//                }}
//        );
//        vCode.setLabelFor(idPrefix + queue * 3 + 2);
//        vCode.setGravity(Gravity.LEFT);
//        vCode.setText(code + ", " + validThru);
//
//        layout.addView(vPaymentSystem);
//        layout.addView(vBalance);
//        layout.addView(vCode);

        final TextView layout = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
        layout.setLayoutParams(
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ) {{
                    setMargins(
                        getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                        getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                        getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                        getResources().getDimensionPixelSize(R.dimen.defaultMargin)
                    );
                }}
        );

        layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.red_frame, null));
        layout.setText(wallet.name());

        layout.setPadding(
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding),
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding),
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding),
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding)
        );

        return layout;
    }

}
