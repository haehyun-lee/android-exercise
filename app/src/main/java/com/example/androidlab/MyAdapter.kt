package com.example.androidlab

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab.databinding.ItemRecyclerviewBinding

// 항목 레이아웃 XML파일에 해당하는 바인딩 객체(항목 뷰)를 가진 ViewHolder
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){ }

// 항목 구성자
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 항목 개수
    override fun getItemCount(): Int {
        return datas.size
    }

    // 항목 뷰를 가지는 뷰 홀더 준비
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    // 각 항목을 구성
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }
}

class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    // 각 항목 꾸미기
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        if (index % 3 == 0) {
            outRect.set(10, 10, 10, 60)
        } else {
            outRect.set(10, 10, 10, 0)
        }

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}