package com.example.ch16_provider

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.ch16_provider.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var filePath: String
    lateinit var requestGalleryLauncher: ActivityResultLauncher<Intent>
    lateinit var requestCameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 퍼미션 허용 확인
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            // 퍼미션 요청 다이얼로그
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>("android.permission.CAMERA",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE"),
                100
            )
        }

        // gallery request launcher
        requestGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                try {
                    binding.userImageView.setImageBitmap(
                        decodeSampledBitmap(
                            it.data!!.data!!,
                            resources.getDimensionPixelSize(R.dimen.imgSize),
                            resources.getDimensionPixelSize(R.dimen.imgSize)
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.galleryButton.setOnClickListener {
            // 갤러리 앱 실행
            val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // camera request launcher
        requestCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                try {
                    binding.userImageView.setImageBitmap(
                        decodeSampledBitmap(
                            Uri.fromFile(File(filePath)),
                            resources.getDimensionPixelSize(R.dimen.imgSize),
                            resources.getDimensionPixelSize(R.dimen.imgSize)
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.cameraButton.setOnClickListener { 
            // 임시 파일 준비
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
            filePath = file.absolutePath

            // content 프로토콜 Uri 생성
            val photoUri: Uri = FileProvider.getUriForFile(
                this,
                "com.example.ch16_provider.fileprovider",
                file
            )
            
            // 카메라 앱 실행
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            requestCameraLauncher.launch(intent)
        }
    }

    // 이미지 축소율 계산
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width:Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            // 기본 1/2배, 2의 제곱을 위한 초기값
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            
            // 1/inSampleSize 배로 축소한 크기
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        
        return inSampleSize
    }

    // 최적 사이즈로 이미지 로딩
    private fun decodeSampledBitmap(fileUri: Uri, reqHeight: Int, reqWidth: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        var inputStream = contentResolver.openInputStream(fileUri)
        BitmapFactory.decodeStream(inputStream, null, options)

        // inSampleSize 최적 비율 계산, 지정
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth)

        options.inJustDecodeBounds = false

        // InputStream은 한번 읽은 Stream은 다시 읽지 못하므로 재생성해야 한다
        inputStream = contentResolver.openInputStream(fileUri)
        // 이미지 로딩(디코드)
        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        inputStream!!.close()
        inputStream = null

        return bitmap!!
    }

    // 권한 요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty()) {
            for (grant in grantResults) {
                // 사용자가 퍼미션을 하나라도 거부하면 앱 종료
                if(grant != PackageManager.PERMISSION_GRANTED)
                    exitProcess(0)
            }
        }
    }
}