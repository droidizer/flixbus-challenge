package com.guru.app.flixbus.misc;

import android.arch.lifecycle.LifecycleObserver;

public interface INeedClickListener extends LifecycleObserver {

    SingleLiveEvent<ClickItemWrapper> getItemClickListenerNotifier();
}