package com.example.androidlab

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {
    override fun onCreate() {
        super.onCreate()
        Log.d("test","MyJobService......onCreate()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test", "MyJobService......onDestroy()")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("test", "MyJobService......onStartJob()")
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("test", "MyJobService......onStopJob()")
        return false
    }

}