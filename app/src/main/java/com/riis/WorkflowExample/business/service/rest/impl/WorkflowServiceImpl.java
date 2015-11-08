package com.riis.WorkflowExample.business.service.rest.impl;

import android.util.Log;

import com.riis.WorkflowExample.business.service.rest.WorkflowServiceRest;
import com.riis.WorkflowExample.listener.IWorkflowListener;
import com.riis.common.rest.dto.WorkflowDto;
import com.riis.common.rest.dto.WorkflowResponseDto;
import com.riis.common.rest.dto.WorkflowType;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Implements the workflow service.
 */
public class WorkflowServiceImpl extends BaseServiceImpl<WorkflowServiceRest> {

    private final static String TAG = "WorkflowServiceImpl";

    public WorkflowServiceImpl() {
        super(WorkflowServiceRest.class);
    }

    public void getWorkflow(final IWorkflowListener listener,
                            final WorkflowType      workflowType) {

        Subscription subscription = getWorkflowCall(workflowType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<WorkflowDto>() {
                    @Override public void onNext(final WorkflowDto workflow) {
                        listener.getWorkflowSuccess(workflow);
                    }

                    @Override public void onCompleted() {
                        // Clean up here, if any
                    }

                    @Override public void onError(final Throwable t) {
                        Log.e(TAG, "onError: ", t);
                        listener.getWorkflowFailure();
                    }
                }
            );
    }

    private Observable<WorkflowDto> getWorkflowCall(final WorkflowType workflowType) {

        return Observable.defer(new Func0<Observable<WorkflowDto>>() {
                @Override public Observable<WorkflowDto> call() {

                    WorkflowServiceRest service = createService();

                    try {
                        WorkflowDto result =
                            service.getWorkflow(workflowType);
                        return Observable.just(result);

                    } catch (RetrofitError e) {
                        Log.e(TAG, "getWorkflow FAILED:", e);
                        return Observable.error(e);
                    }
                }
            });
    }


    public void createWorkflowResponse(
        final IWorkflowListener   listener,
        final WorkflowType        workflowType,
        final WorkflowResponseDto workflowResponse) {

        Subscription subscription = createWorkflowResponseCall(workflowType, workflowResponse)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<WorkflowResponseDto>() {
                    @Override public void onNext(final WorkflowResponseDto response) {
                        listener.putWorkflowResponseSuccess(response);
                    }

                    @Override public void onCompleted() {
                        // Clean up here, if any
                    }

                    @Override public void onError(final Throwable t) {
                        Log.e(TAG, "onError: ", t);
                        listener.putWorkflowResponseFailure();
                    }
                }
            );
    }

    private Observable<WorkflowResponseDto> createWorkflowResponseCall(
        final WorkflowType        workflowType,
        final WorkflowResponseDto response) {

        return Observable.defer(new Func0<Observable<WorkflowResponseDto>>() {
                @Override public Observable<WorkflowResponseDto> call() {

                    WorkflowServiceRest service = createService();

                    try {
                        WorkflowResponseDto result =
                            service.createWorkflowResponse(workflowType, response);
                        return Observable.just(result);

                    } catch (RetrofitError e) {
                        Log.e(TAG, "createWorkflowResponse FAILED:", e);
                        return Observable.error(e);
                    }
                }
            });
    }


}

