package com.guru.app.flixbus.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.guru.app.flixbus.R
import com.guru.app.flixbus.main.viewmodel.MainViewModel
import com.guru.app.flixbus.misc.AndroidBaseInjectableActivity
import javax.inject.Inject

class MainActivity : AndroidBaseInjectableActivity() {

    @Inject
    lateinit var mFactory: MainViewModel.Factory

    @Inject
    lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mFactory).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main, BR.mainViewModel, mViewModel)
    }
}
