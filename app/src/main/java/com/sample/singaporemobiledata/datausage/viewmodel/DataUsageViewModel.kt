package com.sample.singaporemobiledata.datausage.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import com.sample.singaporemobiledata.datausage.model.QuarterModel
import com.sample.singaporemobiledata.datausage.repository.DataUsageRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataUsageViewModel(application: Application) : AndroidViewModel(application) {

    private val mDataUsageRepository = DataUsageRepository(application)
    var mDataUsageData = MutableLiveData<DataUsageModel>()
    private var mConsolidatedQuarterValues = HashMap<String, QuarterModel>()
    private var mQuarterValueMapping = HashMap<String, ArrayList<String>>()
    var mQuarterModelList = MutableLiveData<MutableList<QuarterModel>>()
    fun getMobileDataUsage() {

        mDataUsageRepository.loadMobileDataUsageData()
            .enqueue(object : Callback<DataUsageModel> {
                override fun onFailure(call: Call<DataUsageModel>, t: Throwable) {
                    mDataUsageData.value = null
                }

                override fun onResponse(
                    call: Call<DataUsageModel>,
                    response: Response<DataUsageModel>
                ) {
                    val dataResponse = response.body()
                    mDataUsageData.value = dataResponse
                    dataResponse?.let {
                        processQuarterDataValues(dataResponse)
                    } ?: run {
                        mDataUsageData.value = null

                    }

                }
            })
    }

    fun processQuarterDataValues(data: DataUsageModel): Boolean {
        var quarterValuelist = ArrayList<String>()
        for (m in returnAfter2008Records(data)) {
            m.quarter?.let { quarter->
                val year = quarter.split("-")[0]
                if (mQuarterValueMapping.containsKey(year)) {
                    m.volume_of_mobile_data?.let {
                        quarterValuelist.add(it)
                    }
                    mQuarterValueMapping[year] = quarterValuelist
                } else {
                    quarterValuelist = ArrayList()
                    m.volume_of_mobile_data?.let {
                        quarterValuelist.add(it)
                    }
                    mQuarterValueMapping[year] = quarterValuelist

                }
            }

        }
        if(mQuarterValueMapping.size>0){
            mQuarterValueMapping.toSortedMap()
            val sortedYear = mQuarterValueMapping.toList().sortedBy { (key, _) -> key }.toMap()
              return setQuarterModel(sortedYear)
        }
        return false


    }

     fun setQuarterModel(sortedYear: Map<String, ArrayList<String>>?): Boolean {
        var recentQuarterValue = 0.0

        sortedYear?.let {
            for (i in sortedYear) {
                val model = QuarterModel()
                model.year = i.key
                repeat(i.value.size) {
                    when (it) {
                        0 -> {
                            setModelValues(model, i.value[it], 0, recentQuarterValue)
                            recentQuarterValue = i.value[it].toDouble()
                        }

                        1 -> {
                            setModelValues(model, i.value[it], 1, recentQuarterValue)
                            recentQuarterValue = i.value[it].toDouble()
                        }
                        2 -> {
                            setModelValues(model, i.value[it], 2, recentQuarterValue)
                            recentQuarterValue = i.value[it].toDouble()
                        }
                        3 -> {
                            setModelValues(model, i.value[it], 3, recentQuarterValue)
                            recentQuarterValue = i.value[it].toDouble()
                        }
                    }
                }
                mConsolidatedQuarterValues[i.key] = model
            }
            val sortedFinalMap =
                mConsolidatedQuarterValues.toList().sortedBy { (key, _) -> key }.toMap()
            val quarterModels = sortedFinalMap.values.toMutableList()
            mQuarterModelList.value = quarterModels

            return quarterModels.size>0
        } ?: run {
            return false
        }

    }

     fun setModelValues(
        model: QuarterModel,
        currentQuarterValue: String,
        quarterNo: Int,
        recentQuarterValue: Double
    ): QuarterModel {

        when (quarterNo) {

            0 -> {
                model.quarterOne[currentQuarterValue] =
                    currentQuarterValue.toDouble() >= recentQuarterValue
            }

            1 -> {
                model.quarterTwo[currentQuarterValue] =
                    currentQuarterValue.toDouble() >= recentQuarterValue
            }

            2 -> {
                model.quarterThree[currentQuarterValue] =
                    currentQuarterValue.toDouble() >= recentQuarterValue
            }

            3 -> {
                model.quarterFour[currentQuarterValue] =
                    currentQuarterValue.toDouble() >= recentQuarterValue
            }


        }
        return model

    }
    fun returnAfter2008Records(data: DataUsageModel?): ArrayList<DataUsageModel.Records> {
        val filteredDataSet = ArrayList<DataUsageModel.Records>()
        try {
            data?.result?.let {result ->
                for (i in result.records) {
                    i.quarter?.let { quarter ->
                        if (quarter.split("-")[0].run { toInt() } > 2006) {
                            filteredDataSet.add(i)
                        }
                    }

                }
            }?: run {
                return filteredDataSet
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return filteredDataSet

        }

        return filteredDataSet
    }
}





