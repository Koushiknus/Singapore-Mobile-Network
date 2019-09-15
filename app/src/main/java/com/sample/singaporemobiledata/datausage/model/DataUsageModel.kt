package com.sample.singaporemobiledata.datausage.model

data class DataUsageModel(

    var success: String? = "",
    var result: Result?


) {
    class Result {
        var records = ArrayList<Records>()
    }

    class Records {
        var volume_of_mobile_data: String? = ""
        var quarter: String? = ""
        var _id: Int? = 0

    }
}
