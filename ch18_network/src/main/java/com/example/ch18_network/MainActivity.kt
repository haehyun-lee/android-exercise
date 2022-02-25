package com.example.ch18_network

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var volleyFragment: VolleyFragment
    lateinit var retrofitFragment: RetrofitFragment
    var mode = "volley"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment 동적 제어
        volleyFragment = VolleyFragment()
        retrofitFragment = RetrofitFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, volleyFragment)
            .commit()

        supportActionBar?.title = "Volley Test"

        //

    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_volley) {

        }else if (item.itemId == R.id.menu_retrofit) {

        }
        return super.onOptionsItemSelected(item)
    }
}