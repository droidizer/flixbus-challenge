package com.guru.app.flixbus.di;

import com.guru.app.flixbus.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
