package com.example.majorproject.Api;


import android.app.ProgressDialog;
import android.content.Context;

import com.example.majorproject.R;

public class CustomDialog
{
    public static ProgressDialog showProgressDialog(Context context)
    {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        return dialog;
    }
}
