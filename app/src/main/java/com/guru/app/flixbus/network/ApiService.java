package com.guru.app.flixbus.network;

import com.guru.app.flixbus.model.TimeTable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("network/station/{station_id}/timetable")
    Observable<TimeTable> getTimeTable(@Path("station_id") int stationId);
}

