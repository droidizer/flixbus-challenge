package com.guru.app.flixbus.di;

import com.guru.app.flixbus.network.ApiManager;
import com.guru.app.flixbus.network.IApiManager;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ManagersModule {

    @Binds
    public abstract IApiManager provideFlixbusApiManager(ApiManager weatherApiManager);
}
