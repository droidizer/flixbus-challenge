package com.guru.app.flixbus.utils.rv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.List;

public class AndroidBindableRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

    private RecyclerViewGestureListener mRecyclerViewGestureListener;

    private PageScrollListener mPageScrollListener;

    private PageDescriptor mDefaultPageDescriptor;

    private OnPageChangeListener mOnPageChangeListener;

    private SnapHelper mSnapHelper;

    private final class PageScrollListener extends OnScrollListener {

        private OnPageChangeListener mOnPageChangeListener;

        private int[] mVisiblePosition;

        private PageDescriptor mPageDescriptor;

        private int mPage = 1;

        PageScrollListener(PageDescriptor pageDescriptor) {
            mPageDescriptor = pageDescriptor;
            mPage = mPageDescriptor.getStartPage();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getAdapter() == null) {
                return;
            }
            final LayoutManager layoutManager = recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleItem = getLastVisibleItemPosition(layoutManager);
            if ((totalItemCount - lastVisibleItem) <= mPageDescriptor.getThreshold()) {
                if (mPageDescriptor.getCurrentPage() < (1 + (totalItemCount / mPageDescriptor.getPageSize()))) {
                    mPageDescriptor.setCurrentPage(1 + (totalItemCount / mPageDescriptor.getPageSize()));
                    if (mOnPageChangeListener != null) {
                        mOnPageChangeListener.onPageChangeListener(mPageDescriptor);
                    }
                }
            }
        }

        private int getLastVisibleItemPosition(LayoutManager layoutManager) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                if (mVisiblePosition == null) {
                    mVisiblePosition = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                }
                return ((StaggeredGridLayoutManager) layoutManager)
                        .findLastVisibleItemPositions(mVisiblePosition)[0];
            }
            return 0;
        }

        public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
            mOnPageChangeListener = onPageChangeListener;
            if (mPageDescriptor != null && mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageChangeListener(mPageDescriptor);
            }
        }
    }

    private GestureDetector mGestureDetector;

    public void setOnItemClickListener(ItemClickListener l) {
        if (l == null) {
            return;
        }
        mGestureDetector = new GestureDetector(getContext(),
                mRecyclerViewGestureListener = new RecyclerViewGestureListener(this));
        mRecyclerViewGestureListener.setRecyclerViewClickListener(l);
    }

    public AndroidBindableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public List<?> getDataSet() {
        if (getAdapter() instanceof AndroidBindableRecyclerViewAdapter) {
            return ((AndroidBindableRecyclerViewAdapter) getAdapter()).getDataSet();
        }
        return null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetector != null && mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public PageDescriptor getPageDescriptor() {
        return mDefaultPageDescriptor;
    }

    public void setPageDescriptor(PageDescriptor pageDescriptor) {
        if (mPageScrollListener != null) {
            removeOnScrollListener(mPageScrollListener);
        }
        mDefaultPageDescriptor = pageDescriptor;
        mPageScrollListener = new PageScrollListener(pageDescriptor);
        mPageScrollListener.setOnPageChangeListener(mOnPageChangeListener);
        addOnScrollListener(mPageScrollListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnItemTouchListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnItemTouchListener(this);
    }

    public void setOnPageChangeListener(OnPageChangeListener pageChangeListener) {
        mOnPageChangeListener = pageChangeListener;
        if (mPageScrollListener != null) {
            mPageScrollListener.setOnPageChangeListener(pageChangeListener);
        }
    }

    public void setSnapHelper(SnapHelper snapHelper) {
        if (mSnapHelper != null) {
            mSnapHelper.attachToRecyclerView(null);
        }
        if (snapHelper == null) {
            return;
        }
        mSnapHelper = snapHelper;
        mSnapHelper.attachToRecyclerView(this);
    }
}
