package com.example.androidlab

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast

class MyService : Service() {
    lateinit var messenger: Messenger

    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(Looper.getMainLooper()) {
        // 외부에서 서비스에 데이터를 전달할 때 자동 호출, msg : 외부에서 전달한 메시지
        override fun handleMessage(msg: Message) {
            // 전달받은 메시지 받기
            when (msg.what) {
                10 -> Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_SHORT)
                20 -> Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_SHORT)
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        // Messenger 생성자 매개변수로 Hanlder를 구현한 객체 지정
        messenger = Messenger(IncomingHandler(this))
        // Messenger 객체의 binder 속성은 IBinder 타입, 메시지 전달하기
        return messenger.binder
    }
}