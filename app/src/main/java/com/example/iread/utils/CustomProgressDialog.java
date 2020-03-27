package com.example.iread.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog {
    private ProgressDialog dialog;

    public CustomProgressDialog(Context context) {
        this.dialog = new ProgressDialog(context);
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Loading");
    }

    public void show(Boolean progress) {
        if (progress) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }
}
