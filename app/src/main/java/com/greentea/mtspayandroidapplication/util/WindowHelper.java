package com.greentea.mtspayandroidapplication.util;

import android.app.AlertDialog;
import android.content.Context;

public class WindowHelper {

    public static void showExceptionAlertDialog(Exception e, Context context) {
        AlertDialog exceptionDialog = new AlertDialog.Builder(context)
                .setTitle("Exception: " + e.getClass().getName())
                .setMessage("Message: " + e.getMessage())
                .show();
    }

}
