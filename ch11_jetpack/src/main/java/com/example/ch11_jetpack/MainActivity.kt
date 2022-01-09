package com.example.ch11_jetpack

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch11_jetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    // 뷰 페이저 어댑터 (프래그먼트 어댑터 이용)
    class MyFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments: List<Fragment>
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())   // 하나의 프래그먼트가 하나의 화면
        }

        // 항목 개수 구하기
        override fun getItemCount(): Int = fragments.size

        // 항목을 구성하는 프래그먼트 객체 구하기
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 적용
        setSupportActionBar(binding.toolbar)

        // 드로어 메뉴 토글 버튼 적용
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_open, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 뷰 페이저에 어댑터 적용
        binding.viewpager.adapter = MyFragmentAdapter(this)
    }

    // 메뉴
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 메뉴 XML 적용
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            // 검색어 변경 이벤트
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            // 검색 버튼 클릭 이벤트
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("test", "serach text : $query")
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    // 토글 버튼 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}