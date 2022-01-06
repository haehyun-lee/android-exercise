package com.example.androidlab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
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

                // 채널을 NotificationManager 에 등록
                manager.createNotificationChannel(channel)

                // 채널 Id로 NotificationCompat.builder 생성
                builder = NotificationCompat.Builder(this, channelId)
            } else {
                builder = NotificationCompat.Builder(this)
            }

            // 알림 정보 설정
            builder.setSmallIcon(android.R.drawable.sym_action_chat)          // 스몰 아이콘
            builder.setWhen(System.currentTimeMillis())     // 알림 시각
            builder.setContentTitle("채팅")                  // 타이틀
            builder.setContentText("프로그래밍 스터디")      // 내용

            // Person 객체 생성 (발신인 이름, 아이콘)
            val sender1: Person = Person.Builder()
                .setName("김철수")
                .setIcon(IconCompat.createWithResource(this, R.drawable.user))
                .build()

            val sender2: Person = Person.Builder()
                .setName("이영희")
                .setIcon(IconCompat.createWithResource(this, R.drawable.user))
                .build()

            // Message 객체 생성 (내용, 시간)
            val message1 = NotificationCompat.MessagingStyle.Message(
                "안녕하세요. 김철수입니다.",
                System.currentTimeMillis(),
                sender1
            )
            val message2 = NotificationCompat.MessagingStyle.Message(
                "이영희입니다. 잘 부탁드립니다.",
                System.currentTimeMillis(),
                sender2
            )

            // MessageStyle 적용
            val messageStyle = NotificationCompat.MessagingStyle(sender1)
                .addMessage(message1)
                .addMessage(message2)
            builder.setStyle(messageStyle)

            manager.notify(11, builder.build())
        }
   }
}