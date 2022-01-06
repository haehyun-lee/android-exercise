package com.example.ch10_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.ch10_notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭 시 알림 띄우기
        binding.notificationButton.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder
            // API 레벨 호환성 (SDK 26 이상 / 미만)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 채널 정보 설정
                val channelId = "one-channel"                   // 채널 식별자
                val channelName = "My Channel One"              // 채널 이름
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "My Channel One Description"  // 채널 설명
                    setShowBadge(true)                          // 배지 표시
                    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    setSound(uri, audioAttributes)              // 효과음
                    enableLights(true)                      // 불빛
                }

                // 채널을 NotificationManager에 등록
                manager.createNotificationChannel(channel)

                // 채널로 builder 생성
                builder = NotificationCompat.Builder(this, channelId)
            } else {
                builder = NotificationCompat.Builder(this)
            }

            builder.run {
                // 알림 기본 정보 설정
                setSmallIcon(R.drawable.small)
                setWhen(System.currentTimeMillis())
                setContentTitle("홍길동")
                setContentText("안녕하세요.")
                setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big))
            }

            // RemoteInput 생성
            val KEY_TEXT_REPLY = "key_text_reply"
            var replyLabel: String = "답장"
            var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replyLabel)
                build()
            }
            val replyIntent = Intent(this, ReplyReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(this, 30, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            // 액션 추가
            builder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.send,
                    "답장",
                    replyPendingIntent
                ).addRemoteInput(remoteInput).build()
            )

            manager.notify(11, builder.build())     // 알림 식별자, Notification 객체
        }
    }
}