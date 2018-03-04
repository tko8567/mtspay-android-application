package com.greentea.mtspayandroidapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TechnicalSupportActivity extends AppCompatActivity {

    private static final int TECH_MESSAGE = 0;
    private static final int USER_MESSAGE = 1;

    private LinearLayout mMessageContainer;
    private EditText mMessageEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);

        mMessageContainer = (LinearLayout) findViewById(R.id.message_container);
        mMessageEditor = (EditText) findViewById(R.id.message_editor);
    }


    /**
     * Creates new message box
     * @param text text to fill the box
     * @param type type of the message. Can be either {@link #TECH_MESSAGE} or {@link #USER_MESSAGE}
     * @return View of the message box
     */
    private View createMessageBox(String text, int type) {
        TextView messageBox = new TextView(new ContextThemeWrapper(this, R.style.MessageText));

        /*
        of LinearLayout

            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginRight="@dimen/space"
            android:layout_marginBottom="@dimen/bigContentPadding"
            android:padding="@dimen/increasedContentPadding"
            android:background="@drawable/red_frame"
         */

        messageBox.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ){{
            setMargins(
                0,
                0,
                    // right
                    (type == TECH_MESSAGE)
                            ? getResources().getDimensionPixelSize(R.dimen.space)
                            : 0,
                    // left
                    (type == TECH_MESSAGE)
                            ? 0
                            : getResources().getDimensionPixelSize(R.dimen.space)
            );
        }});
        messageBox.setPadding(
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding),
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding),
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding),
                getResources().getDimensionPixelSize(R.dimen.increasedContentPadding)
        );
        messageBox.setBackgroundResource(R.drawable.red_frame);
        messageBox.setText(text);

        return messageBox;
    }


    public void onSendClick(View v) {
        if (!mMessageEditor.getText().toString().equals(""))
            mMessageContainer.addView(
                    createMessageBox(mMessageEditor.getText().toString(), USER_MESSAGE)
            );
        mMessageContainer.invalidate();
    }
}
