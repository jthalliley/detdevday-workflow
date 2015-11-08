package com.riis.WorkflowExample;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 *  This is our main application class.
 */
public class WorkflowExampleApplication extends Application {

    private static final String TAG = "WorkflowExampleApp";

    private static ObjectGraph objectGraph;
    private static Resources   resources;

    //GLOBAL: These are used throughout the application as globals.
    public static WorkflowExampleApplication application;
    public static Settings                   settings;
    //GLOBAL: End.


    @Override public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(final Thread thread, final Throwable e) {
                    handleUncaughtException(thread, e);
                }
            }
        );

        application = this;
        resources = getResources();
        settings = new Settings();

        // Initiate Dagger dependency injection
        objectGraph = ObjectGraph.create(new WorkflowExampleModule(this));

        ButterKnife.setDebug(BuildConfig.DEBUG);
    }


    //-------------------------------------------------------
    // Access global application state...
    //-------------------------------------------------------
    public static ObjectGraph getObjectGraph() {
        return objectGraph;
    }

    //-------------------------------------------------------
    // Resource convenience methods...
    //-------------------------------------------------------
    public static int getIntegerResource(final int id) {
        return resources.getInteger(id);
    }

    public static String getStringResource(final int id) {
        return resources.getString(id);
    }

    public static String[] getStringArrayResource(final int id) {
        return resources.getStringArray(id);
    }

    public static int getColorResource(final int id) {
        return resources.getColor(id);
    }

    public static Drawable getDrawableResource(final int id) {
        return resources.getDrawable(id);
    }



    private void handleUncaughtException(final Thread thread, final Throwable e) {
        Log.e(TAG, "handleUncaughtException :");
        e.printStackTrace();
        System.exit(1);
    }

}
