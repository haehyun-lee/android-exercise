package com.example.ch18_network.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ch18_network.databinding.ItemMainBinding
import com.example.ch18_network.model.ItemModel

// ViewHolder
class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

// Adapter
class MyAdapter(val context: Context, val datas: MutableList<ItemModel>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    // ItemModel 데이터 리스트를 받아서 ViewHolder 레이아웃의 각 뷰 객체에 뿌리기
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding

        // 데이터 화면 출력
        val model = datas!![position]
        binding.itemTitle.text = model.title
        binding.itemDesc.text = model.description
        binding.itemTime.text = "${model.author} At ${model.publishedAt}"

        // Glide를 이용해 이미지를 가져와 출력
        Glide.with(context)
            .load(model.urlToImage)
            .into(binding.itemImage)
    }
}