package com.example.ch16_provider

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ch16_provider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 퍼미션 허용했는지 확인
        val status = ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS")
        if (status == PackageManager.PERMISSION_GRANTED) {
            Log.d("test", "permission granted")
        } else {
            // 퍼미션 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(this, arrayOf<String>("android.permission.READ_CONTACTS"), 100)
            Log.d("test", "permission denied")
        }

        // ActivityResultLauncher 초기화, 결과 콜백 정의
        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Log.d("test", "Uri : ${it.data!!.data!!}")
                val cursor = contentResolver.query(
                    it.data!!.data!!,
                    arrayOf<String>(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ),
                    null,
                    null,
                    null
                )
                Log.d("test", "cursor size : ${cursor?.count}")

                if (cursor!!.moveToFirst()) {
                    val name = cursor.getString(0)
                    val phone = cursor.getString(1)
                    binding.textView.text = "name: $name, phone: $phone"
                }
            }
        }

        binding.button.setOnClickListener {
            // 주소록 앱 연동
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestLauncher.launch(intent)
        }
    }

    // 다이얼로그에서 퍼미션 허용했는지 확인
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("test", "permission granted")
        } else {
            Log.d("test", "permission denied")
        }
    }
}