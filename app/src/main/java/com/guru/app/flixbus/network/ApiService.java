package com.guru.app.flixbus.network;

import com.guru.app.flixbus.model.TimeTable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiService {

    @Headers("X-Api-Authentication: intervIEW_TOK3n")
    @GET("network/station/{station_id}/timetable")
    Observable<TimeTable> getTimeTable(@Path("station_id") int stationId);
}

