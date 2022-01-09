package com.example.androidlab

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
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
        binding.recyclerView.addItemDecoration(MyDecoration(this))

        datas.add("new data")
        (binding.recyclerView.adapter as MyAdapter).notifyDataSetChanged()
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

// 사용자 정의 ItemDecoration
class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    // 항목 아래 그려질 이미지
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.big),
            0f,
            0f,
            null)
    }

    // 항목 위에 그려질 이미지
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        // 뷰 크기 계산
        val width = parent.width
        val height = parent.height

        // 이미지 크기 계산
        val dr: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // 이미지가 그려질 위치 계산
        val left = width / 2 - drWidth?.div(2) as Int       // 뷰 정가운데 위치에서 그림 절반만큼 왼쪽으로 이동
        val top = height / 2 - drHeight?.div(2) as Int
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    // 개별 항목 데코
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        // 3의 배수 번째 항목에선 아래 여백을 크게 설정
        if (index % 3 == 0) {
            outRect.set(10, 10, 10, 60)     // 각 항목 표시 사각형 영역 정보
        } else {
            outRect.set(10, 10, 10, 0)
        }

        // 색상 지정
        view.setBackgroundColor(Color.LTGRAY)
        ViewCompat.setElevation(view, 20.0f)
    }
}
