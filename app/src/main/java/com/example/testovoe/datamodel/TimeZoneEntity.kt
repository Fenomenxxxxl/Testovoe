package com.example.testovoe.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_zone_table")
data class TimeZoneEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "countryName") var countryName: String,
    @ColumnInfo(name = "cityName") var cityName: String,
    @ColumnInfo(name = "searchName") var searchName:String,
    @ColumnInfo(name = "timeZone") var timeZone:String
)