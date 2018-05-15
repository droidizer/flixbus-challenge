package com.guru.app.flixbus.network;

import com.guru.app.flixbus.model.TimeTable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ApiManager implements IApiManager {

    @Inject
    public ApiManager() {

    }

    @Override
    public Observable<TimeTable> getTimeTable(int stationId) {
        return Observable.just(new TimeTable());
    }
}
