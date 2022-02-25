package com.example.ch18_network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ch18_network.databinding.FragmentVolleyBinding

class VolleyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentVolleyBinding.inflate(inflater, container, false)

        // RecyclerView
        return binding.root
    }
}