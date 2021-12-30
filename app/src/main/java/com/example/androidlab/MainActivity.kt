package com.example.androidlab

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkbox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                Log.d("kkang", "체크박스 클릭")
            }
        })

        binding.button.setOnClickListener {
            Log.d("kkang", "클릭 이벤트")
        }

        binding.button.setOnLongClickListener {
            Log.d("kkang", "롱클릭 이벤트")
            true
        }
    }

}