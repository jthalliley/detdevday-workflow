package com.riis.WorkflowExample;

import android.content.Context;
import android.util.Log;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;


/**
 * This module encapsulates all Dagger-based injection for the UI portion of the application.
 */
@Module(
    injects = {
    },
    complete = false,
    library = true
)
public class UIModule {

    private static final String TAG = "UIModule";

    public UIModule() {
    }

}
