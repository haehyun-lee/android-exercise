package com.example.ch14_receiver

import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.BatteryManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch14_receiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 배터리 정보 얻기
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = registerReceiver(null, intentFilter)

        val status = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            // 전원 공급 중
            when (batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)) {
                BatteryManager.BATTERY_PLUGGED_USB -> {
                    binding.chargingTextView.text = "USB Plugged"
                    binding.chargingImageView.setImageBitmap(
                        BitmapFactory.decodeResource(resources, R.drawable.usb)
                    )
                }
                BatteryManager.BATTERY_PLUGGED_AC -> {
                    binding.chargingTextView.text = "AC Plugged"
                    binding.chargingImageView.setImageBitmap(
                        BitmapFactory.decodeResource(resources, R.drawable.ac)
                    )
                }
            }
        } else {
            // 전원 공급 안됨
            binding.chargingTextView.text = "Not Plugged"
        }
        
        // 배터리 퍼센트
        val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level / scale.toFloat() * 100
        binding.percentResultView.text = "$batteryPct %"
        
        // 버튼 클릭 시 브로드캐스트 리시버 실행
        binding.button.setOnClickListener {
            val intent = Intent(this, MyReceiver::class.java)
            sendBroadcast(intent)
        }
    }
}