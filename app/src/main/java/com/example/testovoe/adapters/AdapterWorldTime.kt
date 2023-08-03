package com.example.testovoe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testovoe.R
import com.example.testovoe.databinding.ItemWorldTimeBinding
import com.example.testovoe.datamodel.WorldTimeEntity
import com.example.testovoe.interfaces.OnTimeClickListener
import com.example.testovoe.utils.TimeDateUtils.conversionCheck
import com.example.testovoe.utils.TimeDateUtils.getCurrentTime
import com.example.testovoe.utils.TimeDateUtils.mDay
import com.example.testovoe.utils.TimeDateUtils.mHour
import com.example.testovoe.utils.TimeDateUtils.mMinute
import com.example.testovoe.utils.TimeDateUtils.mMonth
import com.example.testovoe.utils.TimeDateUtils.mYear

class AdapterWorldTime : ListAdapter<WorldTimeEntity, AdapterWorldTime.WorldTimeViewHolder>(
    DATA_COMPARATOR
) {

    var mListener: OnTimeClickListener? = null

    fun setOnItemClickListener(listener: OnTimeClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldTimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemWorldTimeBinding=
            DataBindingUtil.inflate(inflater, R.layout.item_world_time, parent, false)
        return WorldTimeViewHolder(binding, mListener!!)

    }

    override fun onBindViewHolder(holderWorld: WorldTimeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holderWorld.bind(currentItem)
    }


    class WorldTimeViewHolder(binding: ItemWorldTimeBinding, listener: OnTimeClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        private val mBinding = binding

        init {

            mBinding.cardView.setOnClickListener {
                val mPosition = adapterPosition
                listener.onItemClick(mPosition)
            }

        }

        fun bind(mCurrentItem: WorldTimeEntity) {
            mBinding.wordTimeData = mCurrentItem

            if (conversionCheck) {
                mBinding.tvDate.visibility = View.VISIBLE
                mBinding.tvTime.visibility = View.VISIBLE
                mBinding.tcDate.visibility = View.GONE
                mBinding.tcTime.visibility = View.GONE
                mBinding.tvTime.text = getCurrentTime(
                    mYear,
                    mMonth,
                    mDay,
                    mHour,
                    mMinute,
                    mCurrentItem.timeZone,
                    "hh:mm a"
                )

                mBinding.tvDate.text = getCurrentTime(
                    mYear,
                    mMonth,
                    mDay,
                    mHour,
                    mMinute,
                    mCurrentItem.timeZone,
                    "EEE, MMM d"
                )

            } else {
                mBinding.tcDate.timeZone = mCurrentItem.timeZone
                mBinding.tcTime.timeZone = mCurrentItem.timeZone
                mBinding.tcDate.visibility = View.VISIBLE
                mBinding.tcTime.visibility = View.VISIBLE
                mBinding.tvTime.visibility = View.GONE
                mBinding.tvDate.visibility = View.GONE
                mBinding.tvTime.visibility = View.GONE
            }


        }

    }


    companion object {
        private val DATA_COMPARATOR = object : DiffUtil.ItemCallback<WorldTimeEntity>() {
            override fun areItemsTheSame(
                oldItem: WorldTimeEntity,
                newItem: WorldTimeEntity
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: WorldTimeEntity,
                newItem: WorldTimeEntity
            ): Boolean {
                return oldItem.cityName == newItem.cityName
            }
        }
    }

}