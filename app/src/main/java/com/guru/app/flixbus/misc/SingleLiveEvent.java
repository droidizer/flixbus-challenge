package com.guru.app.flixbus.misc;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private AtomicBoolean mHasPendingChanges = new AtomicBoolean();

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        super.observe(owner, t -> {
            if (mHasPendingChanges.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @Override
    public void setValue(final T value) {
        mHasPendingChanges.set(true);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mHandler.post(() -> SingleLiveEvent.super.setValue(value));
        } else {
            super.setValue(value);
        }
    }

    @MainThread
    public void call() {
        setValue(null);
    }
}
