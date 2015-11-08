package com.riis.WorkflowExample.presenter;

import android.util.Log;

import com.riis.WorkflowExample.WorkflowExampleApplication;
import com.riis.WorkflowExample.activity.WorkflowActivity;
import com.riis.WorkflowExample.business.service.WorkflowService;
import com.riis.WorkflowExample.listener.IWorkflowListener;
import com.riis.common.rest.dto.WorkflowAttributeDto;
import com.riis.common.rest.dto.WorkflowDataType;
import com.riis.common.rest.dto.WorkflowDto;
import com.riis.common.rest.dto.WorkflowResponseDto;
import com.riis.common.rest.dto.WorkflowType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Business logic for WorkflowExampleActivity.
 */
public class WorkflowPresenter implements IWorkflowListener {

    private static final String TAG = "WorkflowPresenter";

    private WorkflowActivity activity;
    private WorkflowService  workflowService;


    public WorkflowPresenter(final WorkflowActivity activity) {
        this.activity = activity;

        workflowService = new WorkflowService();
    }


    public void completeWorkflow(final boolean isCompleted, final WorkflowResponseDto response) {

        if (isCompleted && response != null) {
            workflowService.createWorkflowResponse(this, response);
        }

        activity.setWorkflowCompleted(isCompleted);
    }

    //-------------------------------------------------------
    //  Fetch the workflow to be displayed...
    //-------------------------------------------------------
    public void getWorkflow(final WorkflowType workflowType) {
        activity.showProgressIndicator();
        workflowService.getWorkflow(this, workflowType);
    }

    @Override public void getWorkflowSuccess(final WorkflowDto workflow) {
        activity.getWorkflowSuccess(workflow);
        activity.dismissProgressIndicator();
    }

    @Override public void getWorkflowFailure() {
        activity.dismissProgressIndicator();
        activity.getWorkflowFailure();
    }

    //-------------------------------------------------------
    //  Send workflow response to the server...
    //-------------------------------------------------------
    public void createWorkflowResponse(final WorkflowResponseDto response) {
        activity.showProgressIndicator();
        workflowService.createWorkflowResponse(this, response);
    }

    @Override public void putWorkflowResponseSuccess(final WorkflowResponseDto savedResponse) {
        activity.putWorkflowResponseSuccess(savedResponse);
        activity.dismissProgressIndicator();
    }

    @Override public void putWorkflowResponseFailure() {
        activity.dismissProgressIndicator();
        activity.putWorkflowResponseFailure();
    }


}

