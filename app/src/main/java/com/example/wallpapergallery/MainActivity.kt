package com.example.wallpapergallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.example.wallpapergallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initToolbar()

    }

    //Подключаем toolbar_menu к toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    //Обработчик нажатий на элементы toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        return true
    }

    //Инициализация toolbar
    private fun initToolbar() = with(binding) {
        //Подключаем кастомный toolbar
        setSupportActionBar(toolbar)
        //Смена шрифта у tittle
        toolbar.setTitleTextAppearance(this@MainActivity, R.style.toolbarStyle)
    }
}