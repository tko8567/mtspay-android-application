package com.greentea.mtspayandroidapplication.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.AdapterView;

import java.util.Arrays;

import javax.annotation.Nullable;

public class AppHelper {

    public static void showExceptionAlertDialog(Exception e, Context context,
                                                @Nullable DialogInterface.OnKeyListener onKeyCallback,
                                                @Nullable DialogInterface.OnCancelListener onCancelCallback,
                                                @Nullable AdapterView.OnItemSelectedListener onItemSelectedCallback) {

        AlertDialog exceptionDialog = new AlertDialog.Builder(context)
                .setTitle("Exception: " + e.getClass().getName())
                .setMessage("Message: " + e.getMessage() + '\n'
                            + "Stacktrace:" + Arrays.toString(e.getStackTrace()).replace(",", "\nat "))
                .setOnKeyListener(onKeyCallback)
                .setOnCancelListener(onCancelCallback)
                .setOnItemSelectedListener(onItemSelectedCallback)
                .show();
    }

    public static void showExceptionAlertDialog(Exception e, Context context) {
        showExceptionAlertDialog(e, context, null, null, null);
    }



}
