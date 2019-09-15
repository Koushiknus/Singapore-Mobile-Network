package com.sample.singaporemobiledata.datausage.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.sample.singaporemobiledata.R
import com.sample.singaporemobiledata.datausage.adapter.DataUsageListAdapter
import com.sample.singaporemobiledata.datausage.viewmodel.DataUsageViewModel
import kotlinx.android.synthetic.main.activity_data_usage.*

class DataUsageActivity : AppCompatActivity() {

    private lateinit var mDataUsageViewModel: DataUsageViewModel
    private val mAdapter = DataUsageListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_usage)
        mDataUsageViewModel = this?.run {
            ViewModelProviders.of(this)[DataUsageViewModel::class.java]
        } ?: throw Exception(getString(R.string.invalid_activity))
        initialData()
        listenObservers()

    }

    private fun listenObservers() {

        mDataUsageViewModel.mDataUsageData.observe(this, Observer {
            progressBar.visibility = View.GONE
            it?.let {
                // no operations as of now
            }?:run {
                showErrorMessage()
            }
        })

        mDataUsageViewModel.mQuarterModelList.observe(this, Observer {
            progressBar.visibility = View.GONE
            it?.let {
                mAdapter.setData(it)
                recycleview_data_list.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            } ?: run {
                showErrorMessage()
            }

        })
    }

    private fun showErrorMessage(){
        Toast.makeText(this,getString(R.string.error_message),Toast.LENGTH_LONG).show()
    }

    private fun initialData() {
        // Api Call to get the data from Server
        val mLayoutManager = LinearLayoutManager(this)
        recycleview_data_list.setHasFixedSize(true)
        recycleview_data_list.layoutManager = mLayoutManager
        progressBar.visibility = View.VISIBLE
        mDataUsageViewModel.getMobileDataUsage()
    }

}
