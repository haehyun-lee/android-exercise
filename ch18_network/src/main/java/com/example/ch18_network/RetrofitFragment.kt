package com.example.ch18_network

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_network.databinding.FragmentRetrofitBinding
import com.example.ch18_network.model.PageListModel
import com.example.ch18_network.recycler.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitFragment : Fragment() {
    // 반환한 View 객체를 화면에 출력
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)
        
        // 3. Call 객체 얻기
        val call: Call<PageListModel> = MyApplication.networkService.getList(
            MyApplication.QUERY,
            MyApplication.API_KEY,
            1,
            10
        )
        
        // 4. 네트워크 통신 수행
        call?.enqueue(object : Callback<PageListModel> {
            // 통신 성공
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
                if (response.isSuccessful) {
                    binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.retrofitRecyclerView.adapter = MyAdapter(activity as Context, response.body()?.articles)
                }
            }
            // 통신 실패
            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                call.cancel()
            }
        })

        // RecyclerView 반환
        return binding.root
    }
}