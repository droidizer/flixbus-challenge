package com.guru.app.flixbus.di;

import com.guru.app.flixbus.FlixbusApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@dagger.Component(modules = {
        AndroidSupportInjectionModule.class,
        BuildersModule.class,
        SystemModule.class,
        ManagersModule.class,
        NetworkModule.class})
public interface Component {

    void inject(FlixbusApplication weatherApplication);

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(FlixbusApplication application);

        Component build();
    }
}
