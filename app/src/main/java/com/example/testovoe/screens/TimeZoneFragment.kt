package com.example.testovoe.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testovoe.adapters.AdapterTimeZone
import com.example.testovoe.databinding.FragmentTimeZoneListBinding
import com.example.testovoe.datamodel.TimeZoneEntity
import com.example.testovoe.datamodel.WorldTimeEntity
import com.example.testovoe.interfaces.OnTimeClickListener
import com.example.testovoe.viewModel.WorldTimeViewModel
import com.example.testovoe.viewModel.factory
import java.util.*


class TimeZoneFragment : Fragment() {

    private lateinit var binding: FragmentTimeZoneListBinding

    private val worldTimeViewModel: WorldTimeViewModel by activityViewModels {factory()}
    private lateinit var mAdapter: AdapterTimeZone
    private lateinit var timeZoneEntity: TimeZoneEntity
    private var mZoneList: List<TimeZoneEntity> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeZoneListBinding.inflate(inflater, container, false)

        initViews()
        createRecyclerView()

        worldTimeViewModel.allTimeZoneList.observe(viewLifecycleOwner) { timeZoneList ->
            timeZoneList?.let {
                mAdapter.submitList(it)
                mZoneList = it
                Log.i("information", it.size.toString())
            }
        }

        return binding.root
    }

    private fun initViews() {
        binding.etSearchItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                textFilter(s.toString())
            }
        })

        binding.clearText.setOnClickListener {
            binding.etSearchItem.setText("")
        }
    }

    private fun createRecyclerView() {
        mAdapter = AdapterTimeZone()
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        mAdapter.setOnItemClickListener(object : OnTimeClickListener {
            override fun onItemClick(position: Int) {
                timeZoneEntity = mAdapter.currentList[position]
                worldTimeViewModel.saveSelectedTimeZone(timeZoneEntity)
                requireActivity().onBackPressed()
            }
        })
    }

    private fun textFilter(text: String) {
        val filteredList: ArrayList<TimeZoneEntity> = ArrayList()
        for (item in mZoneList) {
            if (item.searchName.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }
        mAdapter.submitList(filteredList)
    }

}