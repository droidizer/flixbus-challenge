package com.guru.app.flixbus.misc;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ClickDelegate extends ViewModel implements IClickDelegate {

    protected WeakReference<Activity> mActivity;

    @Inject
    public ClickDelegate() {}

    @Override
    public void setActivity(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleClickListener(ClickItemWrapper clickItemWrapper) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void clear() {
        if (mActivity != null) {
            mActivity.clear();
            mActivity = null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        clear();
    }
}
