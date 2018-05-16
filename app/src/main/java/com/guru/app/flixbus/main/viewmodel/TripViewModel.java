package com.guru.app.flixbus.main.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.guru.app.flixbus.misc.AndroidBaseViewModel;
import com.guru.app.flixbus.model.Trip;
import com.guru.app.flixbus.utils.AppUtils;


public class TripViewModel extends AndroidBaseViewModel {

    public static final int TIME_IN_MILLISECONDS = 10000;

    private final Trip mTrip;

    public TripViewModel(Application application, @NonNull Trip trip) {
        super(application);
        mTrip = trip;
    }

    public String getLineCode() {
        return mTrip.getLineCode();
    }

    public String getDirection() {
        return mTrip.getDirection();
    }

    public String getTime() {
        return AppUtils.getTime(mTrip.getDateTime().getTimeStamp() * TIME_IN_MILLISECONDS, mTrip.getDateTime().getTz());
    }
}
