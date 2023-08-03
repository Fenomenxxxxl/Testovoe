package com.example.testovoe.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.testovoe.R
import com.example.testovoe.adapters.AdapterWorldTime
import com.example.testovoe.databinding.DialogDateTimeBinding
import com.example.testovoe.interfaces.OnDateTimeListener
import com.example.testovoe.utils.ScreenUtils.getScreenWidth
import com.example.testovoe.utils.TimeDateUtils.conversionCheck
import com.example.testovoe.utils.TimeDateUtils.getCurrentTime
import org.joda.time.DateTime

class DateTimeDialogHelper(private val context: Context, private val mAdapter: AdapterWorldTime) {

    @SuppressLint("NotifyDataSetChanged")
    fun showDateTimeDialog(zoneName: String) {
        val mDialog = Dialog(context)
        val dialogBinding: DialogDateTimeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_date_time, null, false
        )
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setCancelable(false)
        mDialog.setContentView(dialogBinding.root)

        dialogBinding.dialogLayout.requestLayout()
        dialogBinding.dialogLayout.layoutParams.width =
            (getScreenWidth(context as Activity) * .90).toInt()

        val dtCity = DateTime()

        TimeDateUtils.mYear = dtCity.year
        TimeDateUtils.mMonth = dtCity.monthOfYear
        TimeDateUtils.mDay = dtCity.dayOfMonth
        TimeDateUtils.mHour = dtCity.hourOfDay
        TimeDateUtils.mMinute = dtCity.minuteOfHour

        dialogBinding.tvZoneName.text = zoneName

        dialogBinding.tvDialogTime.text = getCurrentTime(
            TimeDateUtils.mYear,
            TimeDateUtils.mMonth,
            TimeDateUtils.mDay,
            TimeDateUtils.mHour,
            TimeDateUtils.mMinute,
            zoneName,
            "hh:mm a"
        )

        dialogBinding.tvDialogDate.text = getCurrentTime(
            TimeDateUtils.mYear,
            TimeDateUtils.mMonth,
            TimeDateUtils.mDay,
            TimeDateUtils.mHour,
            TimeDateUtils.mMinute,
            zoneName,
            "EEE, MMM d"
        )

        dialogBinding.btnDialogTime.setOnClickListener {
            val newFragment = TimePickerFragment(context)
            newFragment.show((context as FragmentActivity).supportFragmentManager, "timePicker")
            newFragment.onClickListener(object : OnDateTimeListener {
                override fun onItemClick() {
                    dialogBinding.tvDialogTime.text = getCurrentTime(
                        TimeDateUtils.mYear,
                        TimeDateUtils.mMonth,
                        TimeDateUtils.mDay,
                        TimeDateUtils.mHour,
                        TimeDateUtils.mMinute,
                        zoneName,
                        "hh:mm a"
                    )
                }

            })

        }

        dialogBinding.btnDialogDate.setOnClickListener {
            val newFragment = DatePickerFragment(context)
            newFragment.show((context as FragmentActivity).supportFragmentManager, "datePicker")
            newFragment.onClickListener(object : OnDateTimeListener {
                override fun onItemClick() {
                    dialogBinding.tvDialogDate.text = getCurrentTime(
                        TimeDateUtils.mYear,
                        TimeDateUtils.mMonth,
                        TimeDateUtils.mDay,
                        TimeDateUtils.mHour,
                        TimeDateUtils.mMinute,
                        zoneName,
                        "EEE, MMM d"
                    )
                }

            })
        }

        dialogBinding.btnCancel.setOnClickListener {
            mDialog.dismiss()
        }

        dialogBinding.btnDone.setOnClickListener {
            mDialog.dismiss()
            conversionCheck = true
            mAdapter.notifyDataSetChanged()

        }

        mDialog.show()
    }
}