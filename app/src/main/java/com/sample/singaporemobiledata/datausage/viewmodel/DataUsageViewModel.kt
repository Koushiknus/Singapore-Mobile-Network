package com.sample.singaporemobiledata.datausage.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.google.gson.JsonObject
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import com.sample.singaporemobiledata.datausage.repository.DataUsageRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataUsageViewModel(application: Application) : AndroidViewModel(application) {

    private val mDataUsageRepository = DataUsageRepository(application)

    fun getMobileDataUsage(){

        val params = JsonObject()

        mDataUsageRepository.loadMobileDataUsageData(params).enqueue(object : Callback<DataUsageModel>{
            override fun onFailure(call: Call<DataUsageModel>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<DataUsageModel>,
                response: Response<DataUsageModel>
            ) {
                val dataResposne = response.body() as DataUsageModel
                Log.v("DataResponse",dataResposne.success)
            }
        })
    }

}