package com.guru.app.flixbus.model;

public class Trip {

    private String direction;

    private String line_code;

    private DateTime datetime;

    public class DateTime {
        private String tz;
        private long timestamp;

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public long getTimeStamp() {
            return timestamp;
        }

        public void setTimeStamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLineCode() {
        return line_code;
    }

    public void setLineCode(String line_code) {
        this.line_code = line_code;
    }

    public DateTime getDateTime() {
        return datetime;
    }

    public void setDatetime(DateTime datetime) {
        this.datetime = datetime;
    }
}
