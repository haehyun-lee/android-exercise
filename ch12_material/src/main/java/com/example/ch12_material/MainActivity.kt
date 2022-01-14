package com.example.ch12_material

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch12_material.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    
    // ViewPager 어댑터
    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }

        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // ActionBar 내용을 ToolBar에 적용
        setSupportActionBar(binding.toolbar)

        // ActionBarDrawerToggle 버튼 적용
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // ViewPager에 Adapter 적용
        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        // TabLayout, ViewPager 연동
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "Tab${(position + 1)}"
        }.attach()

        // ExtendedFloatingActionButton 이벤트
        binding.fab.setOnClickListener {
            when (binding.fab.isExtended) {
                true -> binding.fab.shrink()
                false -> binding.fab.extend()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴 XML 등록
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 토근 버튼 이벤트
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
