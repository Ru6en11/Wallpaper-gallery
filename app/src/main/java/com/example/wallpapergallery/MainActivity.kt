package com.example.wallpapergallery

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import com.example.wallpapergallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initToolbar()
        initSidebarItemSelectedListener()

    }

    //Подключаем toolbar_menu к toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        //Подключаем searchView
        val manager =getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        //Слушатель для searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //todo
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //todo
                return false
            }

        })

        return true
    }

    //Обработчик нажатий на элементы toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.search -> {
                //todo
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            }
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

    private fun initSidebarItemSelectedListener() = with(binding) {
        sidebar.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> drawerLayout.closeDrawer((GravityCompat.START))
                R.id.favourite -> {
                    //todo
                }
                R.id.history -> {
                    //todo
                }
                R.id.settings -> {
                    //todo
                }
                R.id.about_sidebar -> {
                    //todo
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }
    }
}