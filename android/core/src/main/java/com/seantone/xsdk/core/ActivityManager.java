package com.seantone.xsdk.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import java.lang.ref.WeakReference;

import androidx.annotation.RequiresApi;

public class ActivityManager {
    private WeakReference<Activity> topActivity = null;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    ActivityManager(Context context){
        Activity activity = (Activity)context;
        this.setTopActivity(activity);
        activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                setTopActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (topActivity != null && topActivity.get() == activity) {
                    setTopActivity(null);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    Activity getTopActivity() {
        if(topActivity != null){
            return topActivity.get();
        }
        return null;
    }

    void setTopActivity(Activity topActivity) {
        if(topActivity == null){
            this.topActivity = null;
            return;
        }
        this.topActivity =  new WeakReference<Activity>(topActivity);
    }
}
