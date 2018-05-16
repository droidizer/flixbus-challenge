package com.guru.app.flixbus.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTable {

    @SerializedName("timetable")
    public TimeTableModel timeTableModel;

    public TimeTableModel getTimeTableModel() {
        return timeTableModel;
    }

    public void setTimeTableModel(TimeTableModel timeTableModel) {
        this.timeTableModel = timeTableModel;
    }

    public class TimeTableModel {

        @SerializedName("arrivals")
        public List<Trip> arrivals;

        @SerializedName("departures")
        public List<Trip> departures;

        @SerializedName("message")
        public String message;
    }
}
