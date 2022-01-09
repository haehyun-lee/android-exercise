package com.example.androidlab

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidlab.databinding.ActivityViewpagerBinding
import com.example.androidlab.databinding.ItemPagerBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // RecyclerView 선언된 XML 파일을 메인화면으로 출력
        val binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<String>()
        for (i in 1..10) {
            datas.add("item $i")
        }
        
        // Adapter를 ViewPager에 적용
        binding.viewpager.adapter = MyPagerAdapter(datas)
        binding.viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL
   }
}

// 1. RecyclerView Adapter 이용
class MyPagerViewHolder(val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root) { }

class MyPagerAdapter(val datas: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyPagerViewHolder(ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyPagerViewHolder).binding

        binding.itemPagerTextView.text = datas[position]
        when (position % 3) {
            0 -> binding.itemPagerTextView.setBackgroundColor(Color.RED)    // 0
            1 -> binding.itemPagerTextView.setBackgroundColor(Color.BLUE)   // 1
            2 -> binding.itemPagerTextView.setBackgroundColor(Color.GREEN)  // 2
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}

// 2. Fragment Adapter 이용
class MyFragmenPaperAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    val fragments: List<Fragment>
    init {
        fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        Log.d("kkang", "fragments size : ${fragments.size}")
    }

    // 항목의 개수 반환
    override fun getItemCount(): Int {
        return fragments.size
    }

    // 항목을 구성하는 Fragment 객체 반환, 해당 객체가 각 항목에 출력됨
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}