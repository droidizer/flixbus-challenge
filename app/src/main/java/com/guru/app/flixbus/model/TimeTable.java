package com.guru.app.flixbus.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTable {

    @SerializedName("arrivals")
    public List<Trip> arrivals;

    @SerializedName("departures")
    public List<Trip> departures;

    @SerializedName("message")
    public String message;
}
