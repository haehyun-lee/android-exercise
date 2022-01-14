package com.example.androidlab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.extendedFab.setOnClickListener {
            when (binding.extendedFab.isExtended) {
                true -> binding.extendedFab.shrink()
                false -> binding.extendedFab.extend()
            }
        }
   }
}
