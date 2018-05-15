package com.guru.app.flixbus.utils;

import android.arch.lifecycle.LifecycleObserver;

import com.guru.app.flixbus.misc.ClickItemWrapper;
import com.guru.app.flixbus.misc.SingleLiveEvent;

public interface INeedClickListener extends LifecycleObserver {

    SingleLiveEvent<ClickItemWrapper> getItemClickListenerNotifier();
}