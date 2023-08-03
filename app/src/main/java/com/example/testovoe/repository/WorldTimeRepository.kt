package com.example.testovoe.repository

import androidx.annotation.WorkerThread
import com.example.testovoe.datamodel.TimeZoneEntity
import com.example.testovoe.datamodel.WorldTimeEntity
import com.example.testovoe.interfaces.WorldTimeDao
import kotlinx.coroutines.flow.Flow

class WorldTimeRepository(private val worldTimeDao: WorldTimeDao) {

    val allWorldTimeList: Flow<List<WorldTimeEntity>> = worldTimeDao.getAllWorldTimeList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWorldTime(worldTimeEntity: WorldTimeEntity) {
        worldTimeDao.insertWorldTime(worldTimeEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWorldTime(worldTimeEntity: WorldTimeEntity) {
        worldTimeDao.deleteWorldTime(worldTimeEntity)
    }


    val allTimeZoneList: Flow<List<TimeZoneEntity>> = worldTimeDao.getAllTimeZoneList()


}