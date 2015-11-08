package com.riis.WorkflowExample;

import android.app.Application;

import com.riis.WorkflowExample.BusinessModule;
import com.riis.WorkflowExample.UIModule;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * This module encapsulates all Dagger-based injection for the application.
 */
@Module(
    includes = {
        UIModule.class,
        BusinessModule.class,
    },
    complete = false,
    library = true
)
public final class WorkflowExampleModule {

    private final WorkflowExampleApplication application;

    public WorkflowExampleModule(WorkflowExampleApplication application) {
        this.application = application;
    }

    @Provides @Singleton Application provideApplication() {
        return application;
    }

}
