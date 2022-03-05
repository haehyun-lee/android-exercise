package com.example.ch20_firebase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ch20_firebase.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_gallery -> {
                // 갤러리 이미지 가져오기
            }
            R.id.menu_add_save -> {
                // 스토리지에 이미지 저장

            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 글과 이미지를 파이어스토어에 저장
     *
     */
    private fun saveStore() {
        
    }

    /**
     * 파이어스토어 문서의 식별값을 파일명으로 해서 이미지만 스토리지에 저장
     *
     * @param docId 문서 식별값
     */
    private fun uploadImage(docId: String) {

    }
}