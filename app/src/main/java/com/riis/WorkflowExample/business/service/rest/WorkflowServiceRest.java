package com.riis.WorkflowExample.business.service.rest;

import com.riis.common.rest.dto.WorkflowDto;
import com.riis.common.rest.dto.WorkflowResponseDto;
import com.riis.common.rest.dto.WorkflowType;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * RESTful interface to workflow services.
 */
public interface WorkflowServiceRest {

    @GET("/rest/workflows/{workflowType}")
    @Headers({"Accept: application/json", "Pragma: no-cache", "Accept-Language: en-US"})
    public WorkflowDto getWorkflow(
        @Path("workflowType") final WorkflowType workflowType
    );

    @POST("/rest/workflow-responses/{workflowType}")
    @Headers({"Accept: application/json", "Pragma: no-cache", "Accept-Language: en-US"})
    public WorkflowResponseDto createWorkflowResponse(
        @Path("workflowType") final WorkflowType        workflowType,
        @Body                 final WorkflowResponseDto workflowResponse
    );

}
