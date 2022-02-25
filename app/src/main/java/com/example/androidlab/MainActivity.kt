package com.example.androidlab

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 퍼미션 허용 확인
        if (ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") != PackageManager.PERMISSION_GRANTED) {
            // 퍼미션 요청 다이얼로그
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    "android.permission.INTERNET",
                    "android.permission.ACCESS_NETWORK_STATE"
                ),
                100
            )
        }

        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
            }
        }

        // 서버에 문자열 데이터 요청
        val stringRequest: StringRequest = StringRequest(
            Request.Method.GET,
            "http://www.google.com",
            Response.Listener<String> {
                Log.d("kkang", "server data : $it")
            },
            Response.ErrorListener { error ->
                Log.d("kkang", "error.....$error")
            }
        )

        val stringRequest2 = object : StringRequest(
            Request.Method.POST,
            "http://www.google.com",
            Response.Listener {
                Log.d("kkang", "server data : $it")
            },
            Response.ErrorListener {
                Log.d("kkang", "error......$it")
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf<String, String>("one" to "hello", "two" to "world")
            }
        }

        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)


    }

}

