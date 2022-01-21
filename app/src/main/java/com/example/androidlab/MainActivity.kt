package com.example.androidlab

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var aidlService: IMyAidlInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 서비스 실행
        val intent = Intent("ACTION_AIDL_SERVICE")
        intent.setPackage("com.example.test_outter")
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    // ServiceConnection 객체 생성
    val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlService = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("kkang", "onServiceDisconnected..")
        }
    }

}