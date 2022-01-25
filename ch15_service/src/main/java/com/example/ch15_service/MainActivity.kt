package com.example.ch15_service

import android.annotation.TargetApi
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.example.ch15_outer.MyAIDLInterface
import com.example.ch15_service.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var connectionMode = "none"

    // Messenger
    lateinit var messenger: Messenger
    lateinit var replyMessenger: Messenger
    var messengerJob: Job? = null

    // aidl
    var aidlService: MyAIDLInterface? = null
    var aidlJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Messenger
        onCreateMessengerService()

        // aidl
        onCreateAIDLService()

        // JobScheduler
        onCreateJobScheduler()
    }

    override fun onStop() {
        super.onStop()
        if (connectionMode === "messenger") {
            onStopMessengerService()
        }else if (connectionMode === "aidl") {
            onStopAIDLService()
        }
        connectionMode = "none"
        changeViewEnable()
    }

    fun changeViewEnable() = when (connectionMode) {
        "messenger" -> {
            binding.messengerPlay.isEnabled = true
            binding.aidlPlay.isEnabled = false
            binding.messengerStop.isEnabled = true
            binding.aidlStop.isEnabled = false
        }
        "aidl" -> {
            binding.messengerPlay.isEnabled = false
            binding.aidlPlay.isEnabled = true
            binding.messengerStop.isEnabled = false
            binding.aidlStop.isEnabled = true
        }
        else -> {
            // 초기상태, stop 상태, 두 play 버튼 활성 상태
            binding.messengerPlay.isEnabled = true
            binding.aidlPlay.isEnabled = true
            binding.messengerStop.isEnabled = false
            binding.aidlStop.isEnabled = false

            binding.messengerProgress.progress = 0
            binding.aidlProgress.progress = 0
        }
    }

    // messenger handler
    inner class HandlerReplyMsg : Handler(Looper.getMainLooper()) {
        // 외부 앱의 IcomingHandler의 handleMessage() 함수에서 전송한 데이터 처리
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                10 -> {
                    // 재생 후 시간
                    val bundle = msg.obj as Bundle
                    bundle.getInt("duration")?.let {
                        when {
                            it > 0 -> {
                                // 음악 프로그래스바 진행 (UI 작업은 메인 스레드에서만 가능)
                                binding.messengerProgress.max = it
                                val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
                                messengerJob = backgroundScope.launch {
                                    while (binding.messengerProgress.progress < binding.messengerProgress.max) {
                                        delay(100)
                                        binding.messengerProgress.incrementProgressBy(1000)
                                    }
                                }
                                changeViewEnable()
                            }
                            else -> {
                                connectionMode = "none"
                                unbindService(messengerConnection)
                                changeViewEnable()
                            }
                        }
                    }
                }
            }
        }
    }

    // messenger connection
    val messengerConnection: ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("test", "onServiceConnected...")
            // 서비스에서 반환된 IBinder로 Messenger 생성, 외부 앱 서비스의 핸들러 사용
            messenger = Messenger(service)
            val msg = Message()
            msg.replyTo = replyMessenger    // 해당 메시지에 대한 회신용 메신저
            msg.what = 10
            messenger.send(msg)
            connectionMode = "messenger"
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("test", "onServiceDisconnected...")
        }
    }

    private fun onCreateMessengerService() {
        // 해당 액티비티의 Handler로 Messenger 생성
        replyMessenger = Messenger(HandlerReplyMsg())
        binding.messengerPlay.setOnClickListener{
            // 암시적 인텐트로 외부 앱 연동, 서비스 실행
            val intent = Intent("ACTION_SERVICE_Messenger")
            intent.setPackage("com.example.ch15_outer")
            bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE)
        }
        binding.messengerStop.setOnClickListener {
            // 음악 정지, 외부 앱 서비스 종료
            val msg = Message()
            msg.what = 20
            messenger.send(msg)
            unbindService(messengerConnection)
            messengerJob?.cancel()
            connectionMode = "none"
            changeViewEnable()
        }
    }

    private fun onStopMessengerService() {
        val msg = Message()
        msg.what = 20
        messenger.send(msg)
        unbindService(messengerConnection)
    }

    // aidl connection
    val aidlConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // MyAIDLService에서 반환한 익명 클래스 객체
            aidlService = MyAIDLInterface.Stub.asInterface(service)
            aidlService!!.start()
            binding.aidlProgress.max = aidlService!!.maxDuration
            val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
            aidlJob = backgroundScope.launch {
                while (binding.aidlProgress.progress < binding.aidlProgress.max) {
                    delay(100)
                    binding.aidlProgress.incrementProgressBy(1000)
                }
            }
            connectionMode = "aidl"
            changeViewEnable()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            aidlService = null
        }
    }

    private fun onCreateAIDLService() {
        binding.aidlPlay.setOnClickListener {
            val intent = Intent("ACTION_SERVICE_AIDL")
            intent.setPackage("com.example.ch15_outer")
            bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
        }
        binding.aidlStop.setOnClickListener {
            aidlService!!.stop()
            unbindService(aidlConnection)
            aidlJob?.cancel()
            connectionMode = "none"
            changeViewEnable()
        }
    }

    private fun onStopAIDLService() {
        unbindService(aidlConnection)
    }

    // JobScheduler
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onCreateJobScheduler() {
        // 스마트폰이 와이파이를 이용하는 상황에서 실행되는 잡 스케줄러
        var jobScheduler: JobScheduler? = getSystemService<JobScheduler>()
        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        val jobInfo = builder.build()
        jobScheduler!!.schedule(jobInfo)
    }
}