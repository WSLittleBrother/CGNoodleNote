package com.example.a99351.cgnoodlenote.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by sunshujie on 2017/5/9.
 */

public class PD {
    public static ProgressDialog getProgressDialog(Context context, String title, String msg) {
        ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);

        if (!TextUtils.isEmpty(title)) {
            pd.setTitle(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            pd.setMessage(msg);
        }
        return pd;
    }
}
