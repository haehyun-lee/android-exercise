package com.example.ch16_provider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch16_provider.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        binding.textView.text = intent.getStringExtra("data1")

        binding.button.setOnClickListener {
            intent.putExtra("result", "happy coding")
            setResult(RESULT_OK, intent)
            if(!isFinishing) finish()
        }
    }
}