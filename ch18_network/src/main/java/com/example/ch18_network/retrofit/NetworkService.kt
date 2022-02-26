package com.example.ch18_network.retrofit

import com.example.ch18_network.model.PageListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit 통신용 함수를 선언한 인터페이스
interface NetworkService {
    @GET("/v2/everything")
    fun getList(
        @Query("q") q: String?,
        @Query("apiKey") apiKey: String?,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): Call<PageListModel>
}