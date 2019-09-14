package com.sample.singaporemobiledata.datausage.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sample.singaporemobiledata.R
import com.sample.singaporemobiledata.datausage.viewmodel.DataUsageViewModel

class DataUsageActivity : AppCompatActivity() {

    private lateinit var mDataUsageViewModel: DataUsageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_usage)
        mDataUsageViewModel = this?.run {
            ViewModelProviders.of(this)[DataUsageViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        initialData()

    }

    private fun initialData(){

        mDataUsageViewModel.getMobileDataUsage()

    }

}
