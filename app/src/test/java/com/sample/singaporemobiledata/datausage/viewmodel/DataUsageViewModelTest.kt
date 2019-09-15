package com.sample.singaporemobiledata.datausage.viewmodel

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import com.sample.singaporemobiledata.datausage.model.QuarterModel
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataUsageViewModelTest {

    private lateinit var mDataUsageViewModel: DataUsageViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel() {
        mDataUsageViewModel = DataUsageViewModel(Application())
    }

    @Test
    fun processQuarterDataValues() {
        val data = DataUsageModel("", null)
        val result = mDataUsageViewModel.processQuarterDataValues(data)
        assertSame(result, false)

        val resultSuccess = mDataUsageViewModel.processQuarterDataValues(getDataUsageModel())
        assertSame(true, resultSuccess)
    }

    @Test
    fun setQuarterModel() {
        val result = mDataUsageViewModel.setQuarterModel(null)
        assertSame(result, false)
        val successResult = mDataUsageViewModel.setQuarterModel(getQuarterTestArray())
        assertSame(true,successResult)
    }

    @Test
    fun returnAfter2008Records() {
        val result = mDataUsageViewModel.returnAfter2008Records(null)
        assertSame(0, result.size)
    }

    @Test
    fun setModelValues() {
        val result = mDataUsageViewModel.setModelValues(getQuarterModel(), "1234", 1, 1235.0)
        assertNotNull(result)
        val result2 = mDataUsageViewModel.setModelValues(getQuarterModel(), "1156", 2, 1235.0)
        assertNotNull(result2)
        val result3 = mDataUsageViewModel.setModelValues(getQuarterModel(), "1156", 3, 1235.0)
        assertNotNull(result3)
    }

    private fun getQuarterModel(): QuarterModel {
        var quarterModel = QuarterModel()
        var testHashMap = HashMap<String?, Boolean>()
        testHashMap.put("1234", true)
        quarterModel.year = "2009"
        quarterModel.quarterOne = testHashMap
        return quarterModel

    }

    private fun getQuarterTestArray(): Map<String, ArrayList<String>> {
        val test = HashMap<String, ArrayList<String>>()
        val testQuarters = ArrayList<String>()
        testQuarters.add("1004")
        testQuarters.add("1005")
        testQuarters.add("1005")
        testQuarters.add("1009")
        test.put("2008",testQuarters)

       return  test.toList().sortedBy { (key, _) -> key }.toMap()
    }

    private fun getDataUsageModel(): DataUsageModel {
        val result = DataUsageModel.Result()

        val record1 = DataUsageModel.Records()
        record1.quarter = "2014-Q3"
        record1.volume_of_mobile_data = "8.629095"
        record1._id = 2

        val record2 = DataUsageModel.Records()
        record2.quarter = "2015-Q1"
        record2.volume_of_mobile_data = "9.687363"
        record2._id = 3

        val record3 = DataUsageModel.Records()
        record2.quarter = "2015-Q2"
        record2.volume_of_mobile_data = "10.687363"
        record2._id = 4

        val record4 = DataUsageModel.Records()
        record2.quarter = "2015-Q4"
        record2.volume_of_mobile_data = "11.687363"
        record2._id = 5
        val recordsList = ArrayList<DataUsageModel.Records>()
        recordsList.add(record1)
        recordsList.add(record2)
        recordsList.add(record3)
        recordsList.add(record4)

        result.records = recordsList
        val dataUsageModel = DataUsageModel("true", result)
        return dataUsageModel
    }

}