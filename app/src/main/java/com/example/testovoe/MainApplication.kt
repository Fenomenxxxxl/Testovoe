package com.example.testovoe

import android.app.Application
import com.example.testovoe.repository.WorldTimeRepository
import com.example.testovoe.repository.database.WorldTimeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { WorldTimeDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WorldTimeRepository(database.worldTimeDao()) }
}