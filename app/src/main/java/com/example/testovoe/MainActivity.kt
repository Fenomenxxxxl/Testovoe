package com.example.testovoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testovoe.databinding.ActivityMainBinding
import com.example.testovoe.screens.WorldListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, WorldListFragment())
                .commit()
        }

    }
}