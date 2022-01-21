package com.example.androidlab

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        // 배터리 정보를 반환값인 인텐트 객체의 엑스트라 정보로 등록
        val batteryStatus = registerReceiver(null, intentFilter)

        // 엑스트라 정보에서 배터리 상태 가져오기
        val status = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            // 전원이 공급되고 있음
            val chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            when (chargePlug) {
                BatteryManager.BATTERY_PLUGGED_USB -> Log.d("battery", "usb charge")
                BatteryManager.BATTERY_PLUGGED_AC -> Log.d("battery", "ac charge")
            }
        } else {
            // 전원이 공급되고 있지 않음
            Log.d("battery", "not charging")
        }
    }
}