package com.example.administrator.prenewproject.utils;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ActivityContext implements ActivityLifecycleCallbacks, OnActivityResultListener {

    private Activity activity;
    private boolean main;
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    public void setMain(String main) {

    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public void exitLogin() {
        Iterator<Activity> iterator = activityList.iterator();
        while (iterator.hasNext() && activityList.size() == 1) {
            iterator.next().finish();
            iterator.remove();
        }
    }

    public Activity getCurrentActivity() {
        return activity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e("TAG", "onActivityCreated");
        // AnnotationManager.injectObject(activity, activity);

        if ("com.xlt.wisdomrailway.activity.StewardActivity".equals(activity.getClass().toString())) {

        }

        activity.getWindow().setBackgroundDrawable(null);
        setActivity(activity);
        activityList.add(activity);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("TAG", "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        setActivity(activity);
        Log.e("TAG", "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e("TAG", "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("TAG", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("TAG", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("TAG", "onActivityDestroyed");
        // setActivity(null);
        activityList.remove(activity);
        // OkHttp.cancel();
        OkHttpUtils.getInstance().cancelTag(activity.getClass());
//		if (activity instanceof MainActivity) {
//			FragmentActivity currentActivity = (FragmentActivity) activity;
//			FragmentManager fragmentManager = currentActivity.getSupportFragmentManager();
//			Fragment findFragmentById = fragmentManager.findFragmentById(R.id.content_container);
//
//			// AContext.show(findFragmentById.getClass());
//			OkHttpUtils.getInstance().cancelTag(findFragmentById.getClass());
//		} else {
//			OkHttpUtils.getInstance().cancelTag(activity.getClass());
//		}
    }


    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }
}
