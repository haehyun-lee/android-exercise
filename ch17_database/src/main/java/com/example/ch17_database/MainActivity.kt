package com.example.ch17_database

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch17_database.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add Fab
        val requestAddLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data!!.getStringExtra("result")?.let {
                // AddActivity로 부터 전달받은 데이터를 리스트에 추가
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
        }
        binding.mainFab.setOnClickListener {
            // todo추가 액티비티 실행
            val intent = Intent(this, AddActivity::class.java)
            requestAddLauncher.launch(intent)
        }

        // DB에서 데이터 가져오기
        datas = mutableListOf<String>()

        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from TODO_DB", null)
        cursor.run {
            val columnIndexTodo = getColumnIndex("todo")
            while (moveToNext()) {
                datas?.add(cursor.getString(columnIndexTodo))
            }
        }
        db.close()

        // recyclerView
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    // Menu 등록
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // MenuItem 선택 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 세팅 액티비티 실행
        if (item.itemId == R.id.menu_main_setting) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}