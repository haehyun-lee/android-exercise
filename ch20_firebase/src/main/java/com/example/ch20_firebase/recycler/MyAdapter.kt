package com.example.ch20_firebase.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ch20_firebase.MyApplication
import com.example.ch20_firebase.databinding.ItemMainBinding
import com.example.ch20_firebase.model.ItemData

class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) { }

class MyAdapter(val context: Context, val itemList: MutableList<ItemData>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMainBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            itemEmailView.text = data.email
            itemDateView.text = data.date
            itemContentView.text = data.content
        }
        
        // firebase-ui-storage 라이브러리 이용해 스토리지 이미지 출력
        val imgRef = MyApplication.storage
            .reference
            .child("images/${data.docId}.jpg")
        Glide.with(context)
            .load(imgRef)
            .into(holder.binding.itemImageView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}