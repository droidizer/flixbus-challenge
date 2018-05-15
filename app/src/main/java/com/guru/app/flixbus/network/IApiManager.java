package com.guru.app.flixbus.network;

import com.guru.app.flixbus.model.TimeTable;

import io.reactivex.Observable;

public interface IApiManager {

    Observable<TimeTable> getTimeTable(final int stationId);
}
