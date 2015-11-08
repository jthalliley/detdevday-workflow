package com.riis.WorkflowExample.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.riis.WorkflowExample.R;
import com.riis.WorkflowExample.WorkflowExampleApplication;

/**
 *  Various reusable dialogs.
 */
public class Dialog {

    public void showError(Activity activity, int titleId, int messageId) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity)
            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(WorkflowExampleApplication.getStringResource(titleId))
            .setMessage(WorkflowExampleApplication.getStringResource(messageId))
            .setPositiveButton(R.string.errorDialogOKButton,
                               new DialogInterface.OnClickListener() {
                                   @Override public void onClick(DialogInterface dialog, int which) {
                                       dialog.dismiss();
                                   }
                               })
            .setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

}
