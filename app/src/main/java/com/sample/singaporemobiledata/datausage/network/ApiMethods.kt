package com.sample.singaporemobiledata.datausage.network

import okhttp3.RequestBody
import retrofit2.http.Body


interface ApiMethods{

    fun executeGetDataUsage(@Body mBody : RequestBody)
}