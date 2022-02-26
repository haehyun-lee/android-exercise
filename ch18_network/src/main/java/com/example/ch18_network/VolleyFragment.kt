package com.example.ch18_network

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ch18_network.databinding.FragmentVolleyBinding
import com.example.ch18_network.model.ItemModel
import com.example.ch18_network.recycler.MyAdapter
import org.json.JSONObject

// Volley를 이용해 서버와 연동하고 서버에서 받은 데이터를 Adapter를 활용해 RecyclerView에 출력
class VolleyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentVolleyBinding.inflate(inflater, container, false)
        
        // 서버 url
        val url = MyApplication.BASE_URL + "/v2/everything?q=" +
                "${MyApplication.QUERY}&apiKey=" +
                "${MyApplication.API_KEY}&page=1&pageSize=5"

        // 1. RequestQueue 객체 얻기
        val queue = Volley.newRequestQueue(activity)

        // 2. JSON 데이터 요청 정보
        val jsonRequest = object: JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                // 4. 서버로 부터 결과 데이터를 받는 순간 호출되는 콜백, response는 전달받은 데이터로 JSONObject 타입.
                var jsonArray = response.getJSONArray("articles")       // { articles : [ {}, {}, .. ] }
                var mutableList = mutableListOf<ItemModel>()                  // RecyclerView에 출력할 datas
                for (i in 0 until jsonArray.length()) {
                    ItemModel().run {
                        val article = jsonArray.getJSONObject(i)
                        author = article.getString("author")
                        title = article.getString("title")
                        description = article.getString("description")
                        urlToImage = article.getString("urlToImage")
                        publishedAt = article.getString("publishedAt")
                        mutableList.add(this)
                    }
                }
                binding.volleyRecyclerView.layoutManager = LinearLayoutManager(activity)
                binding.volleyRecyclerView.adapter = MyAdapter(activity as Context, mutableList)
            },
            Response.ErrorListener { error ->
                println("error.............$error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = mutableMapOf<String, String>(
                    "User-agent" to MyApplication.USER_AGENT
                )
                return map
            }
        }

        // 3. 서버에 요청
        queue.add(jsonRequest)
        
        // RecyclerView 반환
        return binding.root
    }
}