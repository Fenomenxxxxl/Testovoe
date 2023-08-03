package com.example.testovoe.interfaces

import androidx.room.*
import com.example.testovoe.datamodel.TimeZoneEntity
import com.example.testovoe.datamodel.WorldTimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldTimeDao {


    @Query("SELECT * FROM world_time_table")
    fun getAllWorldTimeList(): Flow<List<WorldTimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorldTime(worldTimeEntity: WorldTimeEntity)

    @Delete
    suspend fun deleteWorldTime(worldTimeEntity: WorldTimeEntity)

    @Update
    suspend fun updateWorldTime(worldTimeEntity: WorldTimeEntity)

    @Query("DELETE FROM world_time_table")
    suspend fun deleteAllWorldTimeList()



    @Query("SELECT * FROM time_zone_table")
    fun getAllTimeZoneList(): Flow<List<TimeZoneEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTimeZone(timeZoneEntity: TimeZoneEntity)

    @Delete
    suspend fun deleteTimeZone(timeZoneEntity: TimeZoneEntity)

    @Update
    suspend fun updateTimeZone(timeZoneEntity: TimeZoneEntity)

    @Query("DELETE FROM time_zone_table")
    suspend fun deleteAllTimeZone()


}