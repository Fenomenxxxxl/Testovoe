package com.example.testovoe.utils

import com.example.testovoe.datamodel.WorldTimeEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ConversionsUtils {

    fun fromObjectToStringZone(mObject: WorldTimeEntity): String {
        return Gson().toJson(mObject)
    }

    fun fromStringToObjectZone(mString: String): WorldTimeEntity {
        val noteType = object : TypeToken<WorldTimeEntity>() {}.type
        return Gson().fromJson(mString, noteType)
    }


}