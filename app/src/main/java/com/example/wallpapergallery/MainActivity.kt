package com.example.wallpapergallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallpapergallery.fragments.MainFragment
import com.example.wallpapergallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportFragmentManager.beginTransaction()
            .replace(R.id.holder, MainFragment.newInstance()).commit()

    }

}