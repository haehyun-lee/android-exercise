package com.example.ch20_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch20_firebase.databinding.ActivityMainBinding
import com.example.ch20_firebase.model.ItemData
import com.example.ch20_firebase.recycler.MyAdapter
import com.example.ch20_firebase.util.myCheckPermission

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 권한 체크
        myCheckPermission(this)

        // 게시글 추가
        binding.addFab.setOnClickListener {
            if (MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            } else {
                Toast.makeText(this, "인증을 먼저 진행해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 인증 상태 체크
        if (MyApplication.checkAuth()) {
            binding.logoutTextView.visibility = View.GONE
            binding.mainRecyclerView.visibility = View.VISIBLE
            makeRecyclerView()
        } else {
            binding.logoutTextView.visibility = View.VISIBLE
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, AuthActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun makeRecyclerView() {
        // FireStore 컬렉션 모두 가져오기
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                // 리사이클러뷰에 매칭될 데이터 리스트
                val itemList = mutableListOf<ItemData>()

                for (document in result) {
                    // 가져온 문서를 객체에 담기
                    val item : ItemData = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }

                // 리사이클러뷰 출력
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득을 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

    }
}