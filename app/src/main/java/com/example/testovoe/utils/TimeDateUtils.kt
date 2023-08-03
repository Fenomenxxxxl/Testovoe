package com.example.testovoe.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object TimeDateUtils {

    var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var mHour: Int = 0
    var mMinute: Int = 0
    var conversionCheck = false


    fun getCurrentTime(
        mYear: Int,
        mMonth: Int,
        mDay: Int,
        mHour: Int,
        mMinute: Int,
        zone: String,
        formatter: String
    ): String {
        val dt = DateTime(mYear, mMonth, mDay, mHour, mMinute)
        val dtCity = dt.withZone(DateTimeZone.forID(zone))

        return dtCity.toString(formatter)
    }

}
