package com.guru.app.flixbus.utils.rv;


public class AndroidItemBinder {

    private int mLayoutId;

    private int mBindingVariable;

    public AndroidItemBinder(int layoutId, int bindingVariable) {
        mLayoutId = layoutId;
        mBindingVariable = bindingVariable;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public int getDataBindingVariable() {
        return mBindingVariable;
    }

    @Override
    public String toString() {
        return "BindingVariable: " + mBindingVariable + " LayoutId: " + mLayoutId;
    }
}
