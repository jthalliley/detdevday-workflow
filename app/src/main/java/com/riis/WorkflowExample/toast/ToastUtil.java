package com.riis.WorkflowExample.toast;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import com.riis.WorkflowExample.WorkflowExampleApplication;

/**
 *  Toast helpers.
 */
public class ToastUtil {

    public static void show(final Activity activity, final int toastMessageId) {
        ToastUtil.show(activity, WorkflowExampleApplication.getStringResource(toastMessageId));
    }

    public static void show(final Activity activity, final String message) {
        android.widget.Toast toast =
            android.widget.Toast.makeText(activity, message, android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
        toast.show();
    }

}
