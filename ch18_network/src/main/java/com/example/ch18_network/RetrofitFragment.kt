package com.example.ch18_network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ch18_network.databinding.FragmentRetrofitBinding

class RetrofitFragment : Fragment() {
    // 반환한 View 객체를 화면에 출력
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)

        // RecyclerView
        return binding.root
    }
}