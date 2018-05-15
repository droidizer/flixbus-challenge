package com.guru.app.flixbus.utils.rv;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.util.DiffUtil.calculateDiff;

public class AndroidBindableRecyclerViewAdapter extends RecyclerView.Adapter<AndroidBindableRecyclerViewAdapter.BindableViewHolder> {


    static class BindableViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding mViewDataBinding;

        BindableViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
        }
    }

    private Disposable mSetValuesDisposable = Disposables.disposed();

    private ObservableList<?> mDataSet = new ObservableArrayList<>();

    private Map<Class, AndroidItemBinder> mItemBinder;

    private Queue<List<?>> pendingUpdates = new ArrayDeque<>();

    private RecyclerView.RecycledViewPool mSharedPool = new RecyclerView.RecycledViewPool();

    public AndroidBindableRecyclerViewAdapter(Map<Class, AndroidItemBinder> itemsBinder, @Nullable List<?> items) {
        mItemBinder = itemsBinder;
        if (items != null) {
            setDataSet(new ArrayList<>(items));
        }
    }

    public void setDataSet(@Nullable List<?> items) {
        if (items == null) {
            // TODO: decide how to handle it
            return;
        }
        final List<?> oldItems = new ArrayList<>(mDataSet);
        mSetValuesDisposable.dispose();
        mSetValuesDisposable = Observable.just(items)
                .subscribeOn(Schedulers.computation())
                .map((Function<List<?>, Pair<List<?>, DiffUtil.DiffResult>>) newList -> new Pair<>(newList,
                        calculateDiff(new ItemSourceDiffCallback(oldItems, items))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::applyDiffResult, Throwable::printStackTrace);
    }

    private void applyDiffResult(Pair<List<?>, DiffUtil.DiffResult> resultPair) {
        boolean firstStart = true;

        if (!pendingUpdates.isEmpty()) {
            pendingUpdates.remove();
        }

        if (mDataSet.size() > 0) {
            mDataSet.clear();
            firstStart = false;
        }

        if (resultPair.first != null) {
            mDataSet.addAll(new ArrayList(resultPair.first));
        }

        //if we call DiffUtil.DiffResult.dispatchUpdatesTo() on an empty adapter, it will crash - we have to call notifyDataSetChanged()!
        if (firstStart) {
            notifyDataSetChanged();
        } else {
            resultPair.second.dispatchUpdatesTo(this);
        }

        if (pendingUpdates.size() > 0) {
            setDataSet(pendingUpdates.peek());
        }
    }

    public ObservableList<?> getDataSet() {
        return mDataSet;
    }

    @Override
    public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        List<RecyclerView> recyclerViews = getAllRecyclerViews(binding.getRoot());
        for (RecyclerView recyclerView : recyclerViews) {
            recyclerView.setRecycledViewPool(mSharedPool);
        }
        return new BindableViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        Object itemAtPosition = mDataSet.get(position);
        if (itemAtPosition == null) {
            throw new IllegalStateException("Found null item in the dataset " + this + " templates " + mItemBinder + " dataSet " + mDataSet);
        }
        Class clazz = itemAtPosition.getClass();
        AndroidItemBinder rowBinder = mItemBinder.get(clazz);
        if (rowBinder == null) {
            throw new IllegalStateException("There is not Binder for " + clazz.getSimpleName());
        }
        return rowBinder.getLayoutId();
    }

    @Override
    public void onBindViewHolder(BindableViewHolder holder, int position) {
        Object item = mDataSet.get(position);
        final AndroidItemBinder itemBinder = mItemBinder.get(item.getClass());
        holder.mViewDataBinding.setVariable(itemBinder.getDataBindingVariable(), item);
        holder.mViewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    private List<RecyclerView> getAllRecyclerViews(View v) {
        List<RecyclerView> visited = new ArrayList<>();
        List<View> unvisited = new ArrayList<>();
        unvisited.add(v);

        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            if (child instanceof RecyclerView) {
                visited.add((RecyclerView) child);
            }
            if (!(child instanceof ViewGroup)) {
                continue;
            }
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                unvisited.add(group.getChildAt(i));
            }
        }

        return visited;
    }
}