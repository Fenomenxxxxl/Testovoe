package com.example.testovoe.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.testovoe.interfaces.OnDateTimeListener
import com.example.testovoe.utils.TimeDateUtils.mDay
import com.example.testovoe.utils.TimeDateUtils.mHour
import com.example.testovoe.utils.TimeDateUtils.mMinute
import com.example.testovoe.utils.TimeDateUtils.mMonth
import com.example.testovoe.utils.TimeDateUtils.mYear
import java.util.*

class TimePickerFragment(context: Context) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private val mContext = context
    private var mListener: OnDateTimeListener? = null

    fun onClickListener(onDateTimeListener: OnDateTimeListener) {
        mListener = onDateTimeListener
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(mContext, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        mHour = hourOfDay
        mMinute = minute
        mListener?.onItemClick()
    }

}

class DatePickerFragment(context: Context) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val mContext = context
    private var mListener: OnDateTimeListener? = null

    fun onClickListener(onDateTimeListener: OnDateTimeListener) {
        mListener = onDateTimeListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(mContext, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        mYear = year
        mMonth = month + 1
        mDay = day
        mListener?.onItemClick()
    }
}