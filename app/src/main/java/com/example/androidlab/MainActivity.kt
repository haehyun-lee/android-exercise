package com.example.androidlab

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
   }

    // 업 버튼 클릭 이벤트
    override fun onSupportNavigateUp(): Boolean {
        Log.d("kkang", "onSupportNavigateUp")
        return super.onSupportNavigateUp()
    }

    // 메뉴 구성
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            // 검색어 변경 이벤트
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            // 키보드의 검색 버튼을 클릭한 순간의 이벤트
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    // 메뉴 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        0 -> {
            Log.d("kkang", "menu1 click")
            true
        }
        1 -> {
            Log.d("kkang", "menu2 click")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}

