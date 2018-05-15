package com.guru.app.flixbus.utils.rv;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LayoutManagers {

    @IntDef({LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public interface LayoutManagerFactory {

        RecyclerView.LayoutManager create(RecyclerView recyclerView);
    }

    public static LayoutManagerFactory linear() {
        return recyclerView -> new LinearLayoutManager(recyclerView.getContext());
    }

    public static LayoutManagerFactory linear(@Orientation final int orientation, final boolean reverseLayout) {
        return recyclerView -> new LinearLayoutManager(recyclerView.getContext(), orientation, reverseLayout);
    }

    private LayoutManagers() {
    }
}