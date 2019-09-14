package com.sample.singaporemobiledata.datausage.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import com.sample.singaporemobiledata.datausage.model.QuarterModel
import com.sample.singaporemobiledata.datausage.repository.DataUsageRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataUsageViewModel(application: Application) : AndroidViewModel(application) {

    private val mDataUsageRepository = DataUsageRepository(application)
    var mDataUsageData = MutableLiveData<DataUsageModel>()
    var mConsolidatedQuarterValues = HashMap<String,QuarterModel>()
    var mQuarterValueMapping = HashMap<String,ArrayList<String>>()

    fun getMobileDataUsage() {

        mDataUsageRepository.loadMobileDataUsageData()
            .enqueue(object : Callback<DataUsageModel> {
                override fun onFailure(call: Call<DataUsageModel>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<DataUsageModel>,
                    response: Response<DataUsageModel>
                ) {
                    val dataResposne = response.body() as DataUsageModel
                    mDataUsageData.value = dataResposne
                    processQuarterDataValues(dataResposne)
                    Log.v("DataResponse", dataResposne.result.records[0].quarter)
                }
            })
    }

    fun processQuarterDataValues(data : DataUsageModel){
        var listofQuarterValues = ArrayList<String>()
        for(m in returnAfter2018Records(data)){
             val year =    m.quarter!!.split("-")[0]
            if(mQuarterValueMapping.containsKey(year)){
                listofQuarterValues.add(m.volume_of_mobile_data!!)
                mQuarterValueMapping.put(year,listofQuarterValues)
            }else{
                listofQuarterValues = ArrayList()
                listofQuarterValues.add(m.volume_of_mobile_data!!)
                mQuarterValueMapping.put(year,listofQuarterValues)
            }

        }
        mQuarterValueMapping.toSortedMap()
        val sortedYear = mQuarterValueMapping.toList().sortedBy { (key, _) -> key}.toMap()


        for(i in sortedYear){
            //val result = mQuarterValueMapping.entries.sortedWith(compareBy())
            repeat(i.value.size){
                Log.v("Valueretrieved",i.key  +" "+i.value[it])
            }
        }

    }

    private fun setQuarterModel(){

    }

  private  fun returnAfter2018Records(data : DataUsageModel): ArrayList<DataUsageModel.Records> {
        val filteredDataSet = ArrayList<DataUsageModel.Records>()
        try{
            for (i in data.result.records){
                i.quarter?.let { quarter ->
                    if(quarter.split("-")[0].toInt() > 2007){
                        filteredDataSet.add(i)
                    }
                }

            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        return filteredDataSet
    }

}