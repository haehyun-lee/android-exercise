package com.example.ch15_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("test", "onStartJob................")
        // 알림 띄우기
        val manager = getSystemService<NotificationManager>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("oneId", "oneName", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "oneDes"
            manager?.createNotificationChannel(channel)
            Notification.Builder(this, "oneId")
        } else {
            Notification.Builder(this)
        }.run {
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setContentTitle("JobScheduler Title")
            setContentText("Content Message")
            setAutoCancel(true)
            manager?.notify(1, build())
        }
        // 작업이 완벽하게 종료되었음
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // 잡 스케줄러 등록 취소
        return true
    }
}