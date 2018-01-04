package com.greentea.mtspayandroidapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greentea.mtspayandroidapplication.util.Account;

import java.text.DecimalFormat;
import java.util.List;

import static android.widget.RelativeLayout.BELOW;
import static apollo.FindPersonByIdQuery.Card;

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
        List<Card> cards = Account.getInstance().getPerson().cards();
        for (int i = 0; i < cards.size(); i++) {
            mCardsLayout.addView(createViewForCard(cards.get(i), i));
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

    @SuppressWarnings("ConstantConditions")
    private View createViewForCard(Card card, int queue) {

        StringBuilder codeBuilder = new StringBuilder("xxxx ");
        for (int i = 0; i < 4; i++)
            codeBuilder.append(card.number().charAt(card.number().length() - 4 + i));

        DecimalFormat format = new DecimalFormat(getString(R.string.moneyFormat));

        // TODO: 18.12.2017 Update payment system support once server gets one
        String paymentSystem = "VISA";//card.paymentSystem;
        String code = codeBuilder.toString();
        String validThru = getString(R.string.validThru) + card.validThru();
        String balance = format.format(card.balance());
        boolean isDefault = card.isDefault();

        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ){{ setMargins(
                getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                getResources().getDimensionPixelSize(R.dimen.defaultMargin));
        }});
        layout.setBackgroundResource(R.drawable.red_frame);

        final TextView vPaymentSystem = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
        vPaymentSystem.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ) {{
                    setMargins(
                            getResources().getDimensionPixelSize(R.dimen.increasedMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                }}
        );
        if (isDefault) vPaymentSystem.setTextColor(getResources().getInteger(R.color.colorAccent));
        vPaymentSystem.setLabelFor(idPrefix + queue * 3);
        vPaymentSystem.setGravity(Gravity.LEFT|Gravity.TOP);
        vPaymentSystem.setText(paymentSystem);

        TextView vBalance = new TextView(new ContextThemeWrapper(this, R.style.StandardText));
        vBalance.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ) {{
                    setMargins(
                            getResources().getDimensionPixelSize(R.dimen.increasedMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                }}
        );
        vBalance.setLabelFor(idPrefix + queue * 3 + 1);
        vBalance.setGravity(Gravity.RIGHT|Gravity.TOP);
        vBalance.setText(balance);


        TextView vCode = new TextView(new ContextThemeWrapper(this, R.style.SubText));
        vCode.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ) {{
                    setMargins(
                            getResources().getDimensionPixelSize(R.dimen.increasedMargin),
                            0,
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin),
                            getResources().getDimensionPixelSize(R.dimen.defaultMargin));
                    addRule(BELOW, vPaymentSystem.getId());
                }}
        );
        vCode.setLabelFor(idPrefix + queue * 3 + 2);
        vCode.setGravity(Gravity.LEFT);
        vCode.setText(code + ", " + validThru);

        layout.addView(vPaymentSystem);
        layout.addView(vBalance);
        layout.addView(vCode);

        return layout;
    }

}
