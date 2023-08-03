package com.example.testovoe.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.testovoe.MainApplication
import com.example.testovoe.datamodel.TimeZoneEntity
import com.example.testovoe.datamodel.WorldTimeEntity
import com.example.testovoe.repository.WorldTimeRepository
import com.example.testovoe.utils.ConversionsUtils
import com.example.testovoe.utils.Event
import kotlinx.coroutines.launch

class WorldTimeViewModel(
    private val worldTimeRepository: WorldTimeRepository
) : ViewModel() {

    private val _timeZone = MutableLiveData<Map<String, String>>()
    val timeZone = _timeZone

    private val _showDateTimeDialog = MutableLiveData<Event<String>>()
    val showDateTimeDialog: LiveData<Event<String>> = _showDateTimeDialog

    fun onTimeItemClick(position: Int) {
        val timeZone = allWorldTimeList.value?.get(position)?.timeZone
        timeZone?.let {
            _showDateTimeDialog.value = Event(it)
        }
    }


    fun saveSelectedTimeZone(timeZoneEntity: TimeZoneEntity) {
        val worldTimeEntity = WorldTimeEntity(
            countryName = timeZoneEntity.countryName,
            cityName = timeZoneEntity.cityName,
            timeZone = timeZoneEntity.timeZone
        )

        val timeZoneMap = timeZone.value ?: mapOf()
        val newTimeZoneMap =
            timeZoneMap + ("MY_SOME_KEY" to ConversionsUtils.fromObjectToStringZone(worldTimeEntity))
        _timeZone.value = newTimeZoneMap
    }


    val allWorldTimeList: LiveData<List<WorldTimeEntity>> =
        worldTimeRepository.allWorldTimeList.asLiveData()

    fun insertWorldTime(worldTimeEntity: WorldTimeEntity) = viewModelScope.launch {
        worldTimeRepository.insertWorldTime(worldTimeEntity)
    }

    fun deleteWorldTime(worldTimeEntity: WorldTimeEntity) = viewModelScope.launch {
        worldTimeRepository.deleteWorldTime(worldTimeEntity)
    }


    val allTimeZoneList: LiveData<List<TimeZoneEntity>> =
        worldTimeRepository.allTimeZoneList.asLiveData()


    class WorldTimeViewModelFactory(private val repository: WorldTimeRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorldTimeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorldTimeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}

fun Fragment.factory() =
    WorldTimeViewModel.WorldTimeViewModelFactory((requireContext().applicationContext as MainApplication).repository)