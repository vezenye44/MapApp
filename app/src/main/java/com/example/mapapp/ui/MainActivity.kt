package com.example.mapapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.ui.map.MapFragment
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        MapKitFactory.initialize(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(binding.container.id, MapFragment.newInstance(), "").commit()
        }
    }
}