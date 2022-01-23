package com.example.androidlab

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.os.Messenger
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var messenger: Messenger

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var jobScheduler: JobScheduler? = getSystemService<JobScheduler>()
        val extras = PersistableBundle()
        extras.putString("extra_data", "hello world")
        val builder = JobInfo.Builder(1, componentName)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)  // 네트워크 타입
        builder.setRequiresCharging(true)   // 배터리 충전 상태
        builder.setExtras(extras)
        val jobInfo = builder.build()
        jobScheduler!!.schedule(jobInfo)


        JobInfo.Builder(1, ComponentName(this, MyJobService::class.java)).run {
            setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            jobScheduler?.schedule(build())
        }

    }
}