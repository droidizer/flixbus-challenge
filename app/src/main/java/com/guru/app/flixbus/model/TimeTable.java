package com.guru.app.flixbus.model;


import java.util.List;

public class TimeTable {

    public TimeTableModel timeTableModel;

    public TimeTableModel getTimeTableModel() {
        return timeTableModel;
    }

    public void setTimeTableModel(TimeTableModel timeTableModel) {
        this.timeTableModel = timeTableModel;
    }

    public class TimeTableModel {

        public List<Trip> arrivals;

        public List<Trip> departures;

        public String message;
    }
}
