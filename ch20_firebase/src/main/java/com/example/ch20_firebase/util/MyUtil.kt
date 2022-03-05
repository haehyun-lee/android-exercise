package com.example.ch20_firebase.util

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

// 퍼미션 체크
fun myCheckPermission(activity: AppCompatActivity) {
    val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(activity, "권한 승인", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "권한 거부", Toast.LENGTH_SHORT).show()
            }
        }

    // 퍼미션 허용 여부 확인
    if (ContextCompat.checkSelfPermission(activity,
            Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}

// Date 타입을 String 타입으로 형식화
fun dateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(date)
}