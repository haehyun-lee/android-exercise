package com.example.ch20_firebase.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

fun myCheckPermission(activity: AppCompatActivity) {
    val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() {}


    )

}