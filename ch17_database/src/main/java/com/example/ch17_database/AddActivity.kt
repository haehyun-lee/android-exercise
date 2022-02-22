package com.example.ch17_database

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ch17_database.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // MenuItem 선택 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.menu_add_save -> {
                // 데이터베이스 행 삽입
                val inputData = binding.addEditView.text.toString()
                val db: SQLiteDatabase = DBHelper(this).writableDatabase
                db.execSQL("insert into TODO_DB (todo) values (?)",
                    arrayOf<String>(inputData)
                )
                db.close()

                // 데이터 전달 후 액티비티 닫기
                val intent = intent
                intent.putExtra("result", inputData)
                setResult(Activity.RESULT_OK, intent)
                finish()
                true
            }
            else -> true
        }
}