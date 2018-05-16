package com.guru.app.flixbus.main.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.databinding.Bindable;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guru.app.flixbus.BR;
import com.guru.app.flixbus.R;
import com.guru.app.flixbus.misc.AndroidBaseViewModel;
import com.guru.app.flixbus.model.TimeTable;
import com.guru.app.flixbus.model.Trip;
import com.guru.app.flixbus.network.IApiManager;
import com.guru.app.flixbus.utils.rv.AndroidItemBinder;
import com.guru.app.flixbus.utils.rv.ItemClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import retrofit2.HttpException;

public class TimeTableViewModel extends AndroidBaseViewModel {

    public static final int STATION_ID = 10;

    private final IApiManager mApiManager;

    private Disposable mDisposable = Disposables.disposed();

    private boolean mLoading;

    private String mErrorMessage;

    private boolean mErrorVisible;

    private Resources mResources;

    private Map<Class<?>, AndroidItemBinder> mTemplates;

    private List<AndroidBaseViewModel> mListItems;

    @Inject
    public TimeTableViewModel(Application application, IApiManager apiManager, Resources resources) {
        super(application);
        mApiManager = apiManager;
        mResources = resources;
    }

    @Override
    public void onStart() {
        super.onStart();
        setLoading(true);
        mListItems = new ArrayList<>();
        subscribeToDataChanges();
    }

    private void subscribeToDataChanges() {
        if (mDisposable.isDisposed()) {
            mDisposable = mApiManager.getTimeTable(STATION_ID)
                    .filter(response -> response != null)
                    .subscribe(this::accept
                            , throwable -> {
                                setLoading(false);
                                notifyError(throwable);
                            });
        }
    }

    public Map<Class<?>, AndroidItemBinder> getTemplatesForObjects() {
        if (mTemplates == null) {
            mTemplates = new HashMap<>();
            mTemplates.put(TripViewModel.class, new AndroidItemBinder(R.layout.rv_item, BR.tripViewModel));
        }
        return mTemplates;
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        int bottomMargin = (int) mResources.getDimension(R.dimen.margin_4);
        int horizontalMargin = (int) mResources.getDimension(R.dimen.activity_horizontal_margin);
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = bottomMargin;
                outRect.right = horizontalMargin;
                outRect.left = horizontalMargin;
            }
        };
    }

    public ItemClickListener getItemClickListener() {
        return ((view, item) -> {
        });
    }

    @Bindable
    public List<?> getListItems() {
        return mListItems;
    }

    @Bindable
    public String getErrorMessage() {
        return mErrorMessage;
    }

    @Bindable
    public boolean isErrorVisible() {
        return mErrorVisible;
    }

    private void notifyError(Throwable throwable) {
        setLoading(false);
        String errorMessage = (throwable instanceof HttpException || throwable instanceof IOException)
                ? mResources.getString(R.string.connection_error)
                : mResources.getString(R.string.error);
        setErrorMessage(errorMessage);
        setErrorVisible(true);
    }

    public void setErrorMessage(String message) {
        mErrorMessage = message;
        notifyPropertyChanged(BR.errorMessage);
    }

    private void setErrorVisible(boolean errorVisible) {
        mErrorVisible = !isLoading() && errorVisible;
        notifyPropertyChanged(BR.errorVisible);
    }

    public void notifyBindings() {
        notifyPropertyChanged(BR.listItems);
    }

    private void setLoading(boolean loading) {
        mLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isLoading() {
        return mLoading;
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
        mListItems.clear();
        super.onDestroy();
    }

    private void accept(TimeTable trip) {
        if (trip.timeTableModel != null
                && trip.timeTableModel.departures != null
                && !trip.timeTableModel.departures.isEmpty()) {
            for (Trip t : trip.timeTableModel.departures) {
                mListItems.add(new TripViewModel(getApplication(), t));
            }
        }
        setLoading(false);
        notifyBindings();
    }

    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        Application mApplication;
        private IApiManager mApiManager;
        private Resources mResources;

        @Inject
        public Factory(@NonNull Application application, IApiManager apiManager, Resources resources) {
            mApplication = application;
            mApiManager = apiManager;
            mResources = resources;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TimeTableViewModel(mApplication, mApiManager, mResources);
        }
    }
}
