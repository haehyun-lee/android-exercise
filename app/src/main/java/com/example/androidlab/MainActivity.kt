package com.example.androidlab

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    lateinit var binding: ActivityMainBinding
    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 퍼미션 허용 확인
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            // 퍼미션 요청 다이얼로그
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>("android.permission.READ_EXTERNAL_STORAGE"),
                100
            )
        }


        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
            }
        }
        /*
        // 외장메모리 사용 가능 여부 체크
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Log.d("kkang", "ExternalStorageState MOUNTED")
        } else {
            Log.d("kkang", "ExternalStorageState UNMOUNTED")
        }

        // 앱별 저장소에 파일 쓰기, 읽기

        val file: File = File(getExternalFilesDir(null), "test.txt")
        val writeStream: OutputStreamWriter = file.writer()
        writeStream.write("hello world")
        writeStream.flush()

        val readStream: BufferedReader = file.reader().buffered()
        readStream.forEachLine {
            Log.d("kkang", "$it")
        }

        // 외장 메모리의 앱별 저장소 파일을 다른 앱에서 접근
        // 현재 날짜 정보를 가진 Data() 객체를 SimpleDateFormat 형식에 맞춰 형식화
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )

        val filePath = file.absolutePath

        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.androidlab.fileprovider",
            file
        )

        // 카메라 앱 실행
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        requestLauncher.launch(intent)

        */

        /*

        // 공용 저장소 접근
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        // 공용 저장소의 이미지 파일 정보 가져오기
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val thumbnail: Bitmap = applicationContext.contentResolver.loadThumbnail(
                    contentUri, Size(640, 480), null
                )

                binding.resultImageView.setImageBitmap(thumbnail)
            }
        }


         */


        /*
        cursor?.let {
            while (cursor.moveToNext()) {
                Log.d("kkang", "_id : ${it.getLong(0)}, name : ${it.getString(1)}")

                // 이미지 데이터 가져오기
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getLong(0)
                )


                val resolver = applicationContext.contentResolver
                resolver.openInputStream(contentUri).use { stream ->
                    val option = BitmapFactory.Options()
                    option.inSampleSize = 10
                    val bitmap = BitmapFactory.decodeStream(stream, null, option)
                    binding.resultImageView.setImageBitmap(bitmap)
                }
            }
        }

         */

        /*
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        sharedPref.edit().run {
            putString("data1", "hello1")
            putInt("data2", 10)
            commit()
        }

        val data1 = sharedPref.getString("data1", "world")
        val data2 = sharedPref.getInt("data2", 10)
        */
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference,
    ): Boolean {
        // 새로운 Fragment 인스턴스화
        val args = pref.extras
        val fragment =
            supportFragmentManager.fragmentFactory.instantiate(
                classLoader,
                pref.fragment.toString())
        fragment.setTargetFragment(caller, 0)

        // Fragment의 요소를 새로운 Preference로 교체
        // activity_main의 <Fragment> 레이아웃의 클래스 교체
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.setting_content, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }
}

