package com.riis.WorkflowExample.listener;

import com.riis.common.rest.dto.WorkflowDto;
import com.riis.common.rest.dto.WorkflowResponseDto;

public interface IWorkflowListener {

    public void getWorkflowSuccess(WorkflowDto workflow);
    public void getWorkflowFailure();

    public void putWorkflowResponseSuccess(WorkflowResponseDto workflowResponse);
    public void putWorkflowResponseFailure();

}
