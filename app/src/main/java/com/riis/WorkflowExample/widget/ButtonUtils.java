package com.riis.WorkflowExample.widget;

import android.widget.Button;

import com.riis.WorkflowExample.R;
import com.riis.WorkflowExample.WorkflowExampleApplication;

/**
 *  Provides project-specific button utilities, for example, regarding styling.
 */
public class ButtonUtils {

    public static void setEnabled(final boolean enable, final Button button) {

        int textColorId;
        int backgroundColorId;

        if (enable) {
            textColorId       = R.color.button_text_color;
            backgroundColorId = R.color.button_background_color;
        } else {
            textColorId       = R.color.button_disabled_text_color;
            backgroundColorId = R.color.button_disabled_background_color;
        }

        button.setTextColor(WorkflowExampleApplication.getColorResource(textColorId));
        button.setBackgroundResource(backgroundColorId);
        button.setEnabled(enable);
    }

}
