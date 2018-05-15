package com.guru.app.flixbus.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.guru.app.flixbus.FlixbusApplication;

import dagger.Module;
import dagger.Provides;

@Module
class SystemModule {

    @Provides
    public Context providesContext(FlixbusApplication application) {
        return application;
    }

    @Provides
    public Resources providesResources(Context context) {
        return context.getResources();
    }

    @Provides
    public Application providesApplication(FlixbusApplication application){ return application; }
}
