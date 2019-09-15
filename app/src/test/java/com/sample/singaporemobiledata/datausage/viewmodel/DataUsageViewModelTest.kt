package com.sample.singaporemobiledata.datausage.viewmodel

import android.app.Application
import com.sample.singaporemobiledata.datausage.model.DataUsageModel
import junit.framework.TestCase.assertSame
import org.junit.Before
import org.junit.Test

class DataUsageViewModelTest {

    private lateinit var mDataUsageViewModel: DataUsageViewModel

    @Before
    fun setUpViewModel(){
        mDataUsageViewModel = DataUsageViewModel(Application())
    }

    @Test
    fun processQuarterDataValues() {
        val data = DataUsageModel("",null)
        val result = mDataUsageViewModel.processQuarterDataValues(data)
        assertSame(result,false)
    }

    @Test
    fun setQuarterModel(){
        val result = mDataUsageViewModel.setQuarterModel(null)
        assertSame(result,false)
    }

    @Test
    fun returnAfter2008Records(){
        val result = mDataUsageViewModel.returnAfter2008Records(null)
        assertSame(0,result.size)
    }


}