package com.riis.WorkflowExample.business.service;

import android.util.Log;

import com.riis.WorkflowExample.business.service.rest.impl.WorkflowServiceImpl;
import com.riis.WorkflowExample.listener.IWorkflowListener;
import com.riis.common.rest.dto.WorkflowResponseDto;
import com.riis.common.rest.dto.WorkflowType;

/**
 * Workflow Services Layer.
 * This layer is responsible for using either RESTful services or a local database.
 */
public class WorkflowService extends BaseService {

    private static final String TAG = "WorkflowService";

    private WorkflowServiceImpl workflowServiceImpl;


    public WorkflowService() {
        workflowServiceImpl = new WorkflowServiceImpl();
    }

    /**
     * Fetches a workflow of the given type.
     * Results are returned asynchronously to the listener.
     */
    public void getWorkflow(
        final IWorkflowListener listener,
        final WorkflowType      workflowType) {

        workflowServiceImpl.getWorkflow(listener, workflowType);
    }

    /**
     * Creates a workflow response (set of answers).
     * Results are returned asynchronously to the listener.
     */
    public void createWorkflowResponse(
        final IWorkflowListener   listener,
        final WorkflowType        workflowType,
        final WorkflowResponseDto workflowResponseDto) {

        workflowServiceImpl.createWorkflowResponse(listener, workflowType, workflowResponseDto);
    }

}
