package com.example.ch20_firebase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ch20_firebase.databinding.ActivityAddBinding
import com.example.ch20_firebase.util.dateToString
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    val requestLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                // 갤러리에서 선택한 이미지 출력
                Glide.with(applicationContext)
                    .load(it.data?.data)
                    .apply(RequestOptions().override(250, 200))
                    .centerCrop()
                    .into(binding.addImageView)

                // 이미지 절대경로 구하기
                val cursor = contentResolver.query(
                    it.data?.data as Uri,
                    arrayOf<String>(MediaStore.Images.Media.DATA),
                    null,
                    null,
                    null
                );

                cursor?.moveToFirst().let {
                    filePath = cursor?.getString(0) as String
                }
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_gallery -> {
                // 갤러리 이미지 가져오기
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                requestLauncher.launch(intent)
            }
            R.id.menu_add_save -> {
                if (binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()) {
                    // 스토어에 데이터 저장 후 document id 값으로 이미지 파일명 지정 (데이터는 FireStore, 이미지는 Storage 에 저장)
                    saveStore()
                } else {
                    Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 글과 이미지를 파이어스토어에 저장
     *
     */
    private fun saveStore() {
        val data = mapOf(
            "email" to MyApplication.email,
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date())
        )
        MyApplication.db.collection("news")
            .add(data)
            .addOnSuccessListener {
                // 파이어스토어에 문서 저장 후 id값을 이미지 파일명으로 저장
                uploadImage(it.id)
            }
            .addOnFailureListener{
                Log.w("kkang", "data save error", it)
            }
    }

    /**
     * 파이어스토어 문서의 식별값을 파일명으로 해서 이미지만 스토리지에 저장
     *
     * @param docId 문서 식별값
     */
    private fun uploadImage(docId: String) {
        val storage = MyApplication.storage
        // 스토리지를 참조하는 StorageReference 생성
        val storageRef : StorageReference = storage.reference
        // 실제 업로드하는 파일을 참조하는 StorageReference 생성
        val imgRef: StorageReference = storageRef.child("images/${docId}.jpg")
        // 파일 경로를 이용해 파일 업로드
        var file = Uri.fromFile(File(filePath))
        val uploadTask = imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener{
                Log.d("kkang", "failure........." + it)
            }
    }
}