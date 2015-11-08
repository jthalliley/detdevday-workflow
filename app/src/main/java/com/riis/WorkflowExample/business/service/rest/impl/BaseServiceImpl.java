package com.riis.WorkflowExample.business.service.rest.impl;

import android.util.Log;

import com.riis.WorkflowExample.R;
import com.riis.WorkflowExample.WorkflowExampleApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * All services must extend this base.
 */
public class BaseServiceImpl<T> {

    private final static String TAG = "BaseServiceImpl";

    private Class<T> classType;

    public BaseServiceImpl(Class<T> classType) {
        this.classType = classType;
    }

    protected T createService() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(new LoggingInterceptor(classType.getSimpleName()));

        okHttpClient.setConnectTimeout(
            WorkflowExampleApplication.getIntegerResource(R.integer.httpConnectionTimeoutInSeconds), TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(
            WorkflowExampleApplication.getIntegerResource(R.integer.httpReadTimeoutInSeconds),       TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(
            WorkflowExampleApplication.getIntegerResource(R.integer.httpWriteTimeoutInSeconds),      TimeUnit.SECONDS);

        Gson gsonAdapter = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeAdapter())
            .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(WorkflowExampleApplication.settings.getBaseURL())
            .setClient(new OkClient(okHttpClient))
            .setLogLevel(RestAdapter.LogLevel.FULL) //Development only
            .setConverter(new GsonConverter(gsonAdapter))
            .build();

        return restAdapter.create(classType);
    }

}

