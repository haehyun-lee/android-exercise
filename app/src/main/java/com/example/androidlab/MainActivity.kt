package com.example.androidlab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // 알림
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 채널 생성
                val channelId = "one-channel"
                val channelName = "My Channel One"
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

                // 채널 정보 설정
                channel.description = "My Channel One Description"      // 채널 설명
                channel.setShowBadge(true)                              // 배지 표시
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                channel.setSound(uri, audioAttributes)                      // 알림음 재생
                channel.enableLights(true)                              // 불빛 표시
                channel.lightColor = Color.RED                              // 불빛 색상
                channel.enableVibration(true)                       // 진동 울림
                channel.vibrationPattern = longArrayOf(100, 200, 100, 200)  // 진동 패턴

                // 채널을 NotificationManager에 등록
                manager.createNotificationChannel(channel)

                // 채널 Id로 NotificationCompat.builder 생성
                builder = NotificationCompat.Builder(this, channelId)
            } else {
                builder = NotificationCompat.Builder(this)
            }

            // 알림 정보 설정
            builder.setSmallIcon(android.R.drawable.sym_action_call)    // 스몰 아이콘
            builder.setWhen(System.currentTimeMillis())     // 알림 시각
            builder.setContentTitle("전화")        // 타이틀
            builder.setContentText("김철수님에게 전화가 왔습니다.")       // 내용

            // 액션 등록
            val actionIntent = Intent(this, ReplyReceiver::class.java)
            val actionPendingIntent = PendingIntent.getBroadcast(this, 20, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.sym_action_call,
                    "전화 받기",
                    actionPendingIntent
                ).build()
            )
            builder.addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.stat_notify_missed_call,
                    "전화 거절",
                    actionPendingIntent
                ).build()
            )

            manager.notify(11, builder.build())
        }
    }
}