package com.guru.app.flixbus.misc;

public class ClickItemWrapper<T> {

    private int mClickedItemId;

    private T mAdditionalData;

    private ClickItemWrapper() {}

    public int getClickedItemId() {
        return mClickedItemId;
    }

    public T getAdditionalData() {
        return mAdditionalData;
    }

    public static <T> ClickItemWrapper<T> withAdditionalData(int id, T additionalData) {
        ClickItemWrapper<T> clickItemWrapper = new ClickItemWrapper<>();
        clickItemWrapper.mAdditionalData = additionalData;
        clickItemWrapper.mClickedItemId = id;
        return clickItemWrapper;
    }

    public static ClickItemWrapper simpleClickItemWrapper(int id) {
        ClickItemWrapper clickItemWrapper = new ClickItemWrapper();
        clickItemWrapper.mClickedItemId = id;
        return clickItemWrapper;
    }
}