package com.example.androidlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab.databinding.ActivityRecyclerviewBinding
import com.example.androidlab.databinding.ItemMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // RecyclerView 선언된 XML 파일을 메인화면으로 출력
        val binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View 객체에 연결할 데이터 세트
        val datas = mutableListOf<String>()
        for(i in 1..10){
            datas.add("Item $i")
        }

        // RecyclerView 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
   }
}

// ViewHolder
class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

// Adapter
class MyAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    // ViewHolder, 뷰 객체 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // ViewHolder의 뷰 객체에 데이터 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("kkang", "onBindViewHolder")
        val binding = (holder as MyViewHolder).binding

        // 뷰에 데이터 출력
        binding.itemData.text = datas[position]

        // 뷰에 이벤트 등록
        binding.itemRoot.setOnClickListener {
            Log.d("kkang", "item root click : $position")
        }
    }

    // 총 데이터 개수 반환
    override fun getItemCount(): Int {
        return datas.size
    }
}