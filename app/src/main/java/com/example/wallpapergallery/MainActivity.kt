package com.example.wallpapergallery

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.wallpapergallery.fragments.MainFragment
import com.example.wallpapergallery.databinding.ActivityMainBinding
import java.io.File
import java.lang.Exception
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportFragmentManager.beginTransaction()
            .replace(R.id.holder, MainFragment.newInstance()).commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        deleteCache(this)
    }

    private fun deleteCache(context: Context) {
        try {
            val dir: File = context.cacheDir
            deleteDir(dir)
        } catch (exp: Exception) {
            println(exp)
        }
    }

    private fun deleteDir(dir: File): Boolean {
        if (dir != null && dir.isDirectory) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                var success = deleteDir((File(dir, children[i])))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else if (dir != null && dir.isFile) {
            return dir.delete()
        } else {
            return false
        }
    }

}