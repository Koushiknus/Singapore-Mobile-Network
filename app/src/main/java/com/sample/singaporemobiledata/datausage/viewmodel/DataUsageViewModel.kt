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
    var mConsolidatedQuarterValues = HashMap<String, QuarterModel>()
    var mQuarterValueMapping = HashMap<String, ArrayList<String>>()

    var mFinalDataUsageByYear = MutableLiveData<Map<String, QuarterModel>>()

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

    fun processQuarterDataValues(data: DataUsageModel) {
        var listofQuarterValues = ArrayList<String>()
        for (m in returnAfter2008Records(data)) {
            val year = m.quarter!!.split("-")[0]
            if (mQuarterValueMapping.containsKey(year)) {
                listofQuarterValues.add(m.volume_of_mobile_data!!)
                mQuarterValueMapping.put(year, listofQuarterValues)
            } else {
                listofQuarterValues = ArrayList()
                listofQuarterValues.add(m.volume_of_mobile_data!!)
                mQuarterValueMapping.put(year, listofQuarterValues)
            }

        }
        mQuarterValueMapping.toSortedMap()
        val sortedYear = mQuarterValueMapping.toList().sortedBy { (key, _) -> key }.toMap()
        setQuarterModel(sortedYear)


    }

    private fun setQuarterModel(sortedYear: Map<String, ArrayList<String>>) {
        var recentQuarterValue  = 0.0

        for(i in sortedYear){
            var model  = QuarterModel()
            model.year = i.key
            repeat(i.value.size){
                when(it){
                    0 ->{
                        setModelValues(model,i.value[it],0,recentQuarterValue)
                        recentQuarterValue = i.value[it].toDouble()
                    }

                    1 -> {
                        setModelValues(model,i.value[it],1,recentQuarterValue)
                        recentQuarterValue = i.value[it].toDouble()
                    }
                    2 -> {
                        setModelValues(model,i.value[it],2,recentQuarterValue)
                        recentQuarterValue = i.value[it].toDouble()
                    }
                    3 -> {
                        setModelValues(model,i.value[it],3,recentQuarterValue)
                        recentQuarterValue = i.value[it].toDouble()
                    }
                }
            }
            mConsolidatedQuarterValues.put(i.key,model)
        }
        val sortedFinalMap = mConsolidatedQuarterValues.toList().sortedBy { (key, _) -> key }.toMap()
        mFinalDataUsageByYear.value = sortedFinalMap


    }

    private fun setModelValues(model : QuarterModel,currentQuarterValue : String, quarterNo : Int,recentQuarterValue : Double): QuarterModel {

        when(quarterNo){

            0 ->{
                if(currentQuarterValue.toDouble()<recentQuarterValue){
                    model.quarterOne.put(currentQuarterValue,false)
                }else{
                    model.quarterOne.put(currentQuarterValue,true)
                }
            }

            1 ->{
                if(currentQuarterValue.toDouble()<recentQuarterValue){
                    model.quarterTwo.put(currentQuarterValue,false)
                }else{
                    model.quarterTwo.put(currentQuarterValue,true)
                }
            }

            2 ->{
                if(currentQuarterValue.toDouble()<recentQuarterValue){
                    model.quarterThree.put(currentQuarterValue,false)
                }else{
                    model.quarterThree.put(currentQuarterValue,true)
                }
            }

            3 ->{
                if(currentQuarterValue.toDouble()<recentQuarterValue){
                    model.quarterFour.put(currentQuarterValue,false)
                }else{
                    model.quarterFour.put(currentQuarterValue,true)
                }
            }


        }
        return model

    }
    }

    private fun returnAfter2008Records(data: DataUsageModel): ArrayList<DataUsageModel.Records> {
        val filteredDataSet = ArrayList<DataUsageModel.Records>()
        try {
            for (i in data.result.records) {
                i.quarter?.let { quarter ->
                    if (quarter.split("-")[0].toInt() > 2007) {
                        filteredDataSet.add(i)
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return filteredDataSet
    }

