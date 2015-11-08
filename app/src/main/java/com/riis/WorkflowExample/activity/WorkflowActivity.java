package com.riis.WorkflowExample.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.riis.WorkflowExample.R;
import com.riis.WorkflowExample.activity.helper.WorkflowTree;
import com.riis.WorkflowExample.dialog.ProgressIndicator;
import com.riis.WorkflowExample.listener.IWorkflowListener;
import com.riis.WorkflowExample.presenter.WorkflowPresenter;
import com.riis.WorkflowExample.toast.ToastUtil;
import com.riis.WorkflowExample.widget.ButtonUtils;
import com.riis.common.rest.dto.WorkflowDto;
import com.riis.common.rest.dto.WorkflowResponseDto;
import com.riis.common.rest.dto.WorkflowType;


/**
 * Our main activity, where workflows are displayed.
 */
public class WorkflowActivity extends Activity implements IWorkflowListener {

    private static final String TAG = "WorkflowActivity";

    @Bind(R.id.buttonMedicalHistory)  Button buttonMedicalHistory;
    @Bind(R.id.buttonPersonalInfo)    Button buttonPersonalInfo;

    @Bind(R.id.buttonComplete) Button       buttonComplete;
    @Bind(R.id.inner)          LinearLayout inner;

    private WorkflowActivity  activity;
    private WorkflowPresenter presenter;
    private WorkflowType      whichWorkflowIsDisplayed;
    private WorkflowTree      workflowTree;

    private ProgressIndicator progressIndicator;


    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow_example);
        activity = this;

        ButterKnife.bind(this);

        presenter = new WorkflowPresenter(this);

        //        ButtonUtils.setEnabled(false, buttonComplete);
    }

    @OnClick(R.id.buttonMedicalHistory) public void medicalHistoryOnClick() {
        whichWorkflowIsDisplayed = WorkflowType.MEDICAL_HISTORY;
        presenter.getWorkflow(whichWorkflowIsDisplayed);
    }

    @OnClick(R.id.buttonPersonalInfo) public void personalInfoOnClick() {
        //TODO:        presenter.getWorkflow(WorkflowType.PERSONAL_INFO);
        whichWorkflowIsDisplayed = WorkflowType.PERSONAL_INFO;
        presenter.getWorkflow(whichWorkflowIsDisplayed);
    }


    //-------------------------------------------------------
    // Dealing with the progress indicator...
    //-------------------------------------------------------
    public void showProgressIndicator() {
        int titleId;
        int messageId;

//        if (kind == ProgressIndicator.ProgressKind.GET_WORKFLOW) {
            titleId   = R.string.progressFetchWorkflowTitle;
            messageId = R.string.progressFetchWorkflowMessage;
//        } else {
//            titleId   = R.string.progressSaveWorkflowTitle;
//            messageId = R.string.progressSaveWorkflowMessage;
//        }

        progressIndicator = new ProgressIndicator(this, titleId, messageId);
        progressIndicator.show();
    }

    public void dismissProgressIndicator() {
        progressIndicator.dismiss();
    }


    @OnClick(R.id.buttonComplete) public void completeWorkflow() {

        //DEBUG:                WorkflowTree.PrettyPrinter.print(workflowTree, 0);

        WorkflowResponseDto response    = WorkflowTree.traverse(workflowTree);
        boolean             isCompleted = WorkflowTree.isCompletedWorkflow(workflowTree);

        //DEBUG:                WorkflowTree.PrettyPrinter.print(workflowTree, 0);

        presenter.completeWorkflow(isCompleted, whichWorkflowIsDisplayed, response);
    }

    public void setWorkflowCompleted(final boolean isCompleted) {
        //TODO:  perhaps disable any more work on current screen?!?!?
    }


    //-------------------------------------------------------
    // Deal with workflow service callbacks...
    //-------------------------------------------------------
    @Override public void getWorkflowSuccess(final WorkflowDto workflow) {
        setupInitialWorkflow(workflow);
        dismissProgressIndicator();
    }

    @Override public void getWorkflowFailure() {
        dismissProgressIndicator();
        ToastUtil.show(this, R.string.errorGetWorkflowFailed);
    }

    @Override public void putWorkflowResponseSuccess(final WorkflowResponseDto response) {
        dismissProgressIndicator();
        ToastUtil.show(this, R.string.successPutWorkflowSaved);
    }

    @Override public void putWorkflowResponseFailure() {
        dismissProgressIndicator();
        ToastUtil.show(this, R.string.errorPutWorkflowResponseFailed);
    }


    private void setupInitialWorkflow(WorkflowDto workflow) {

        inner.removeAllViews();

        workflowTree = WorkflowTree.createStep(this, null, workflow);

        inner.addView(workflowTree.outerContainer);
    }

}

