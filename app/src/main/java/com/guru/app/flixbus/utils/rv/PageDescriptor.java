package com.guru.app.flixbus.utils.rv;

public class PageDescriptor {

    private int mStartPage = 1;

    private int mPageSize = 7;

    private int mThreshold = 5;

    private int mCurrentPage;

    public static class PageDescriptorBuilder {

        private int mStartPage = 1;

        private int mPageSize = 7;

        private int mThreshold = 5;

        public PageDescriptorBuilder setStartPage(int startPage) {
            mStartPage = startPage;
            return this;
        }

        public PageDescriptorBuilder setPageSize(int pageSize) {
            mPageSize = pageSize;
            return this;
        }

        public PageDescriptorBuilder setThreshold(int threshold) {
            mThreshold = threshold;
            return this;
        }

        public PageDescriptor build() {
            return new PageDescriptor(this);
        }
    }

    private PageDescriptor(PageDescriptorBuilder builder) {
        mStartPage = builder.mStartPage;
        mPageSize = builder.mPageSize;
        mThreshold = builder.mThreshold;
        mCurrentPage = mStartPage;
    }

    public int getStartPage() {
        return mStartPage;
    }

    public PageDescriptor setStartPage(int startPage) {
        mStartPage = startPage;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PageDescriptor setPageSize(int pageSize) {
        mPageSize = pageSize;
        return this;
    }

    public int getThreshold() {
        return mThreshold;
    }

    public PageDescriptor setThreshold(int threshold) {
        mThreshold = threshold;
        return this;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public PageDescriptor setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
        return this;
    }

}