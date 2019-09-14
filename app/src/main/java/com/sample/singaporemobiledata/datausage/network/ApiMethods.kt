package com.sample.singaporemobiledata.datausage.network

import com.sample.singaporemobiledata.BuildConfig
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import retrofit2.Call
import retrofit2.http.GET


interface ApiMethods{

    @GET(BuildConfig.API_SERVER_URL)
    fun executeGetDataUsage() : Call<DataUsageModel>
}