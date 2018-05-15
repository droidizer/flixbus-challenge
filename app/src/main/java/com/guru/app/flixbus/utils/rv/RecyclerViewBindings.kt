package com.blackbelt.bindings.recyclerviewbindings

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import com.guru.app.flixbus.utils.rv.*

private val KEY_ITEMS = -1024

@BindingAdapter("itemViewBinder")
fun setItemViewBinder(recyclerView: RecyclerView,
                      itemViewMapper: Map<Class<*>, AndroidItemBinder>) {
    setItemViewBinder(recyclerView, itemViewMapper, true)
}

@BindingAdapter("itemViewBinder", "nestedScrollingEnabled")
fun setItemViewBinder(recyclerView: RecyclerView, itemViewMapper: Map<Class<*>, AndroidItemBinder>,
                      nestedScrollingEnabled: Boolean) {
    val items = recyclerView.getTag(KEY_ITEMS) as List<Any>?
    if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
        (recyclerView.adapter as AndroidBindableRecyclerViewAdapter).setDataSet(items)
        return
    }
    val adapter = AndroidBindableRecyclerViewAdapter(itemViewMapper, items)
    recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter
}

@BindingAdapter("items")
fun <T> setItems(recyclerView: RecyclerView, items: List<Any>) {
    recyclerView.setTag(KEY_ITEMS, items)
    if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
        (recyclerView.adapter as AndroidBindableRecyclerViewAdapter).setDataSet(items)
    }
}

@BindingAdapter("layoutManager")
fun <T> setLayoutManager(recyclerView: RecyclerView,
                         layoutManager: LayoutManagers.LayoutManagerFactory) {
    val manager = layoutManager.create(recyclerView)
    manager.isAutoMeasureEnabled = true
    recyclerView.layoutManager = manager
}

@BindingAdapter("itemDecoration")
fun addDividerItemDecoration(recyclerView: RecyclerView, itemDecoration: RecyclerView.ItemDecoration) {
    recyclerView.addItemDecoration(itemDecoration)
}

@BindingAdapter("pageDescriptor")
fun setPageDescriptor(recyclerView: AndroidBindableRecyclerView, pageDescriptor: PageDescriptor) {
    if (recyclerView.pageDescriptor != pageDescriptor) {
        recyclerView.pageDescriptor = pageDescriptor
    }
}

@InverseBindingAdapter(attribute = "pageDescriptor")
fun getPageDescriptor(recyclerView: AndroidBindableRecyclerView): PageDescriptor? {
    return recyclerView.pageDescriptor
}

@BindingAdapter("snapHelper")
fun setSnapHelper(recyclerView: AndroidBindableRecyclerView, snapHelper: SnapHelper) {
    recyclerView.setSnapHelper(snapHelper)
}

@BindingAdapter("onItemClickListener")
fun setOnItemClickListener(recyclerView: RecyclerView, clickListener: ItemClickListener) {
    if (recyclerView is AndroidBindableRecyclerView) {
        recyclerView.setOnItemClickListener(clickListener)
    }
}

