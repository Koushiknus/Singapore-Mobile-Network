package com.sample.singaporemobiledata.datausage.network

import com.sample.singaporemobiledata.BuildConfig
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiMethods{

    @POST(BuildConfig.API_SERVER_URL)
    fun executeGetDataUsage(@Body mBody: RequestBody) : Call<DataUsageModel>
}