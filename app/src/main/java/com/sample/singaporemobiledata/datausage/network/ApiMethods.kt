package com.sample.singaporemobiledata.datausage.network

import com.sample.singaporemobiledata.BuildConfig
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiMethods{

    @POST(BuildConfig.API_SERVER_URL)
    fun executeGetDataUsage(@Body mBody : RequestBody)
}