package com.guru.app.flixbus.misc;


import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

public interface IClickDelegate extends LifecycleObserver {

    void setActivity(Activity activity);

    void handleClickListener(ClickItemWrapper clickItemWrapper);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void clear();
}
