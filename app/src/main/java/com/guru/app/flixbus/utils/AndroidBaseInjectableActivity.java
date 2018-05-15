package com.guru.app.flixbus.utils;


import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.guru.app.flixbus.misc.ClickDelegate;
import com.guru.app.flixbus.misc.ClickItemWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class AndroidBaseInjectableActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private AndroidBaseViewModel mAndroidBaseViewModel;

    private List<INeedClickListener> mINeedClickListenerList = new ArrayList<>();

    protected ViewDataBinding mViewDataBinding;

    private final List<LifecycleObserver> mLifecycleObservers = new ArrayList<>();

    protected ClickDelegate mClickDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        createClickDelegate();
    }

    protected void createClickDelegate() {
        mClickDelegate = ViewModelProviders.of(this).get(ClickDelegate.class);
        mClickDelegate.setActivity(this);
        registerForLifeCycle(mClickDelegate);
    }

    protected <T extends INeedClickListener> void subscribeClickEvent(List<T> listeners) {
        mINeedClickListenerList.addAll(listeners);
        for (INeedClickListener l : listeners) {
            if (l != null) {
                l.getItemClickListenerNotifier()
                        .observe(this, this::handleClickListener);
                registerForLifeCycle(l);
            }
        }
    }

    public void setContentView(int layoutResID, int bindingVariable, AndroidBaseViewModel androidViewModel) {
        if (androidViewModel != null) {
            mAndroidBaseViewModel = androidViewModel;
            mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
            mViewDataBinding.setVariable(bindingVariable, androidViewModel);
            registerForLifeCycle(androidViewModel);
            setContentView(mViewDataBinding.getRoot());
            subscribeClickEvent();
        } else {
            setContentView(layoutResID);
        }
    }

    private void subscribeClickEvent() {
        subscribeClickEvent(Collections.singletonList(mAndroidBaseViewModel));
    }

    protected void handleClickListener(ClickItemWrapper clickItemWrapper) {
        if (mClickDelegate != null) {
            mClickDelegate.handleClickListener(clickItemWrapper);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAndroidBaseViewModel != null) {
            getLifecycle().removeObserver(mAndroidBaseViewModel);
        }
        for (INeedClickListener l : mINeedClickListenerList) {
            l.getItemClickListenerNotifier().removeObservers(this);
            getLifecycle().removeObserver(l);
        }

        for (LifecycleObserver lifecycleObserver : mLifecycleObservers) {
            getLifecycle().removeObserver(lifecycleObserver);
        }
        mLifecycleObservers.clear();
        mINeedClickListenerList.clear();
    }

    public void registerForLifeCycle(LifecycleObserver lifecycleObserver) {
        if (!mLifecycleObservers.contains(lifecycleObserver)) {
            mLifecycleObservers.add(lifecycleObserver);
            getLifecycle().addObserver(lifecycleObserver);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
