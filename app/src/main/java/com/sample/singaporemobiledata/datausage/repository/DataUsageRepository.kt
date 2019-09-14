package com.sample.singaporemobiledata.datausage.repository

import android.app.Application
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import com.sample.singaporemobiledata.datausage.network.Api
import retrofit2.Call

class DataUsageRepository(private val mApplication: Application) {

    fun loadMobileDataUsageData(): Call<DataUsageModel> {
        val client = Api.getApiService().executeGetDataUsage()
        return client

    }
}