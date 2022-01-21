package com.example.androidlab

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return object : IMyAidlInterface.Stub() {
            override fun funA(data: String?) {
            }

            override fun funB(): Int {
                return 10
            }
        }
    }
}