package com.guru.app.flixbus.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AppUtils {

    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.GERMAN);
    private static final SimpleDateFormat sTimeFormat = new SimpleDateFormat("HH:mm", Locale.GERMAN);

    public static String getTodayDate() {
        return getFormattedDate(0);
    }

    public static String getFormattedDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return sDateFormat.format(calendar.getTime());
    }

    public static String getFormattedDate(long dateTime, String timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sDateFormat.format(calendar.getTime());
    }

    public static String getTime(long dateTime, String timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sTimeFormat.format(calendar.getTime());
    }
}
