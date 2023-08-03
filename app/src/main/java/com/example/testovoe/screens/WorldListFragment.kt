package com.example.testovoe.screens

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testovoe.R
import com.example.testovoe.adapters.AdapterWorldTime
import com.example.testovoe.adapters.ItemTouchHelperCallback
import com.example.testovoe.databinding.FragmentWordListBinding
import com.example.testovoe.datamodel.WorldTimeEntity
import com.example.testovoe.interfaces.OnTimeClickListener
import com.example.testovoe.utils.*
import com.example.testovoe.utils.TimeDateUtils.conversionCheck
import com.example.testovoe.viewModel.WorldTimeViewModel
import com.example.testovoe.viewModel.factory

class WorldListFragment : Fragment(), MenuProvider {
    private lateinit var dateTimeDialogHelper: DateTimeDialogHelper
    private lateinit var binding: FragmentWordListBinding
    private lateinit var mAdapter: AdapterWorldTime
    private var zoneList: List<WorldTimeEntity> = ArrayList()
    private val worldTimeViewModel: WorldTimeViewModel by activityViewModels {factory()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordListBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(
            this@WorldListFragment,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
        initViews()
        createRecyclerView()
        observeViewModel()
        dateTimeDialogHelper = DateTimeDialogHelper(requireContext(), mAdapter)

        worldTimeViewModel.timeZone.observe(viewLifecycleOwner) { map ->
            map?.let {
                val reply = it["MY_SOME_KEY"]
                val mTimeZone = ConversionsUtils.fromStringToObjectZone(reply.toString())
                zoneComparison(mTimeZone)
            }

        }

        return binding.root
    }

    private fun observeViewModel() {
        worldTimeViewModel.allWorldTimeList.observe(viewLifecycleOwner) { worldTimeList ->
            worldTimeList?.let {
                mAdapter.submitList(it)
                zoneList = it
                if (it.isNotEmpty()) {
                    binding.clockEmptyText.visibility = View.GONE
                } else {
                    binding.clockEmptyText.visibility = View.VISIBLE
                }
            }
            worldTimeViewModel.showDateTimeDialog.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { zoneName ->
                    dateTimeDialogHelper.showDateTimeDialog(zoneName)
                }
            }
        }
    }

    private fun createRecyclerView() {
        mAdapter = AdapterWorldTime()
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        mAdapter.setOnItemClickListener(object : OnTimeClickListener {
            override fun onItemClick(position: Int) {
                worldTimeViewModel.onTimeItemClick(position)
            }
        })

        val itemTouchHelperCallback =
            ItemTouchHelperCallback(object : ItemTouchHelperCallback.OnSwipeListener {
                override fun onSwiped(position: Int, direction: Int) {
                    val worldTimeEntity = mAdapter.currentList[position]
                    worldTimeViewModel.deleteWorldTime(worldTimeEntity)
                    showMessage("Элемент удален!")
                }
            })

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        binding.addTimeZone.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TimeZoneFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.zone_menu, menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onMenuItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_refresh -> {
                conversionCheck = false
                mAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun zoneComparison(mTimeZone: WorldTimeEntity) {
        if (!zoneList.any { it.cityName == mTimeZone.cityName }) {
            worldTimeViewModel.insertWorldTime(mTimeZone)
        }
    }
}