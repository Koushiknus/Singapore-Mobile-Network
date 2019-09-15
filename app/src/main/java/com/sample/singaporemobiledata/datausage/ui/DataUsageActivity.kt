package com.sample.singaporemobiledata.datausage.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.sample.singaporemobiledata.R
import com.sample.singaporemobiledata.datausage.adapter.DataUsageListAdapter
import com.sample.singaporemobiledata.datausage.viewmodel.DataUsageViewModel
import kotlinx.android.synthetic.main.activity_data_usage.*

class DataUsageActivity : AppCompatActivity() {

    private lateinit var mDataUsageViewModel: DataUsageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_usage)
        mDataUsageViewModel = this?.run {
            ViewModelProviders.of(this)[DataUsageViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        initialData()
        listenObservers()

    }

    private fun listenObservers(){
        mDataUsageViewModel.mFinalDataUsageByYear.observe(this, Observer {
            for(j in it!!){
                Log.v("Finalretrieved", j.key + " " + j.value.quarterOne + " " + j.value.quarterTwo + " "+j.value.quarterThree +" " +j.value.quarterFour)
            }

            mDataUsageViewModel.mListofQuarterModel.observe(this, Observer {
                progressBar.visibility = View.GONE
                val adapter = DataUsageListAdapter(this)
                adapter.setData(it!!)
                val mLayoutManager = LinearLayoutManager(this)
                recycleview_data_list.setHasFixedSize(true)
                recycleview_data_list.layoutManager = mLayoutManager
                recycleview_data_list.adapter = adapter
                adapter.notifyDataSetChanged()
            })


        })
    }

    private fun initialData() {
        // Api Call to get the data from Server
        progressBar.visibility = View.VISIBLE
        mDataUsageViewModel.getMobileDataUsage()
    }

}
