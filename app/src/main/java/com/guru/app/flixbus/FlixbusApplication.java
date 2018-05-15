package com.guru.app.flixbus;

import android.app.Activity;
import android.app.Application;

import com.guru.app.flixbus.di.DaggerComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class FlixbusApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidActivityInjector;
    }
}
