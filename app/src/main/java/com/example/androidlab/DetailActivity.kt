package com.example.androidlab

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 자신을 실행한 인텐트 객체 얻기
        val intent = intent
        // 인텐트에서 데이터 가져오기
        var data1 = intent.getStringExtra("data1")
        var data2 = intent.getIntExtra("data2", 0)

        Log.d("data1", data1.toString())
        Log.d("data2", data2.toString())

        binding.btnSend.setOnClickListener {
            intent.putExtra("resultData", "world")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}