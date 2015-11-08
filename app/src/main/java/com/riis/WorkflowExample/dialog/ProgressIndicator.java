package com.riis.WorkflowExample.dialog;

import android.app.ProgressDialog;
import android.app.Activity;

import com.riis.WorkflowExample.WorkflowExampleApplication;

import java.util.Date;

/**
 * Shows/hides progress indicator during long communications.
 */
public class ProgressIndicator {

    Date           startProgress;
    ProgressDialog progressDialog;
    Activity       activity;
    int            titleId;
    int            messageId;

    public ProgressIndicator(Activity activity, int titleId, int messageId) {
        this.activity  = activity;
        this.titleId   = titleId;
        this.messageId = messageId;
    }

    /**
     * Displays progress indicator during service interaction with back end.
     */
    public void show() {
        startProgress = new Date();

        progressDialog = ProgressDialog.show(activity,
                                             WorkflowExampleApplication.getStringResource(titleId),
                                             WorkflowExampleApplication.getStringResource(messageId),
                                             /*indeterminate=>*/ true,
                                             /*cancelable=>*/ false);
    }

    /**
     * Dismisses no progress indicator before its time. :P
     */
    public void dismiss() {
        Date endProgress = new Date();

        long diff = endProgress.getTime() - startProgress.getTime();

        if (diff < 500) {
            // Ensure dialog stays up for at least 0.5 seconds
            try {
                Thread.sleep(500 - diff);
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        progressDialog.dismiss();
    }

}
