package com.riis.WorkflowExample.business.service.rest.impl;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 *  Allows us to log the time for a back end API call.
 */
public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "LoggingInterceptor";

    private String whichCall;

    public LoggingInterceptor(String whichCall) {
        this.whichCall = whichCall;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        Log.i(TAG, "Call to " + whichCall + " took " + (t2 - t1) / 1e6d + " milliseconds.");

        return response;
    }
}

