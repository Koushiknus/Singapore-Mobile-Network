package com.sample.singaporemobiledata.datausage.repository

import android.app.Application
import com.google.gson.JsonObject
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import com.sample.singaporemobiledata.datausage.network.Api
import com.sample.singaporemobiledata.datausage.utils.Constants
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call

class DataUsageRepository(private val mApplication: Application) {

   fun loadMobileDataUsageData (params:JsonObject): Call<DataUsageModel> {
       val client = Api.getApiService().executeGetDataUsage(
           RequestBody.create(MediaType.parse(Constants.APPLICATION_JSON),params.toString()))
       return client

   }
}