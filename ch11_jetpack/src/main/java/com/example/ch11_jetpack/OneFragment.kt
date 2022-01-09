package com.example.ch11_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch11_jetpack.databinding.FragmentOneBinding
import com.example.ch11_jetpack.databinding.ItemRecyclerviewBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// ViewHolder
class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) { }

// Adapter
class MyAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 항목 개수를 판단하기 위해 자동 호출
    override fun getItemCount(): Int {
        return datas.size
    }
    
    // 항목 뷰를 가지는 뷰 홀덜르 준비하기 위해 자동 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 각 항목에 데이터를 연결하기 위해 자동 호출
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        // 뷰에 데이터 출력
        binding.itemData.text = datas[position]
    }
}

// ItemDecoration
class MyDecortation(val context: Context) : RecyclerView.ItemDecoration() {
    // 모든 항목이 화면에 배치된 후 호출
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // 뷰 크기 계산
        val width = parent.width
        val height = parent.height

        // 이미지 크기 계산
        val dr: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // 이미지 위치 계산 (화면 정가운데 출력)
        val left = width / 2 - drWidth?.div(2) as Int
        val top = height / 2 - drHeight?.div(2) as Int

        // 계산한 위치에 이미지 그리기
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    // 항목 하나당 한 번씩 호출되어 각 항목을 꾸민
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // 현재 항목의 순서
        val index = parent.getChildAdapterPosition(view)

        // 3의 배수 순서마다 아래 여백 키우기
        if (index % 3 == 0) {
            outRect.set(10, 10, 10, 60)     // left, top, right, bottom
        } else {
            outRect.set(10, 10, 10, 10)
        }

        // 항목 배경색
        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        // 기준 높이 설정
        ViewCompat.setElevation(view, 20.0f)
    }
}


class OneFragment : Fragment() {
    // 반환된 뷰 객체(레이아웃)를 화면에 출력
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        // RecyclerView 를 위한 데이터
        val datas = mutableListOf<String>()
        for (i in 1..9) {
            datas.add("Item $i")
        }

        // RecyclerView 에 Adapter, LayoutManager, ItemDecoration 적용
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(MyDecortation(activity as Context))

        // 루트 뷰 객체 반환 (RecyclerView)
        return binding.root
    }
}