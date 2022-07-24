package com.example.wallpapergallery

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.example.wallpapergallery.adapters.RecyclerViewSearchAdapter
import com.example.wallpapergallery.adapters.ViewPagerAdapter
import com.example.wallpapergallery.databinding.ActivityMainBinding


import com.example.wallpapergallery.fragments.*
import com.example.wallpapergallery.listeners.RecyclerViewWallpaperOnScrollListener
import com.example.wallpapergallery.viewmodels.MainFragmentViewModel
import com.google.android.material.tabs.TabLayoutMediator

import java.io.File
import java.lang.Exception


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainFragmentViewModel
    private val adapter = RecyclerViewSearchAdapter()
    private lateinit var layoutManager: GridLayoutManager
    lateinit var mainToolbarLayout: RelativeLayout

    private val fragmentsList = listOf(
        CategoryFragment.newInstance(),
        RandomFragment.newInstance(),
        AuthorFragment.newInstance(),
        PopularFragment.newInstance()
    )

    private val fragmentListTittle = listOf(
        "CATEGORY",
        "RANDOM",
        "AUTHOR'S",
        "POPULAR"
    )

//    lateinit var binding: ActivityMainBinding
    private var backPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        model = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        mainToolbarLayout = binding.mainToolbarLayout

        initToolbar()
        initSidebarItemSelectedListener()


        if (model.searchState.value == null) {
            model.initSearchState(
                MainFragmentViewModel.SearchState(
                    visible = false,
                    query = ""
                ))
        }

        model.searchState.observe(this) {
            renderSearchState(it)
        }

        //Задаем адаптер для ViewPager2
        val adapter = ViewPagerAdapter(this, fragmentsList)
        binding.viewPager2.adapter = adapter

        //Устанавливаем default tab
        binding.viewPager2.currentItem = 1

        //Синхронизация работы TabLayout и ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, pos ->
            tab.text =fragmentListTittle[pos]
        }.attach()

        layoutManager = GridLayoutManager(this, 2)
        initSearchRecyclerView()

        if (model.state.value == null) {
            model.initState()
        }

        model.state.observe(this) {
            renderState(it)
        }

    }

    private fun renderSearchState(searchState: MainFragmentViewModel.SearchState) = with(binding){

        searchHolder.visibility = if (searchState.visible) View.VISIBLE else View.GONE
    }

    //Подключаем toolbar_menu к toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        //Подключаем searchView
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        val menuSearchItem = menu.findItem(R.id.search)

        if (model.searchState.value?.visible!!) {
            menuSearchItem.expandActionView()
            searchView.setQuery(model.searchState.value?.query, false)
        }

        menuSearchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {

                binding.searchHolder.visibility = View.VISIBLE
                model.switchIsSearchVisible(true)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                model.clearState()
                adapter.clearAdapter()
                binding.searchHolder.visibility = View.GONE
                model.switchIsSearchVisible(false)
                return true
            }


        })


        //Слушатель для searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                adapter.clearAdapter()
                model.clearState()
                model.searchWallpaper(query!!)
                model.setSearchQueryParams(query)
                binding.searchRecyclerView.addOnScrollListener(
                    RecyclerViewWallpaperOnScrollListener(layoutManager,
                    model::searchWallpaper, query, binding.mainToolbarLayout)
                )
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })

        //fixme
        return true
    }


    //Обработчик нажатий на элементы toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        return true
    }

    //Инициализация toolbar
    private fun initToolbar() = with(binding) {
        //Подключаем кастомный toolbar
        (this@MainActivity).setSupportActionBar(toolbar)
        //Смена шрифта у tittle
        toolbar.setTitleTextAppearance(this@MainActivity, R.style.toolbarStyle)
    }

    //слушатель нажатий для sidebar
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

    private fun initSearchRecyclerView() = with(binding) {
        searchRecyclerView.layoutManager = layoutManager
        searchRecyclerView.adapter = adapter


    }

    private fun renderState(state: MainFragmentViewModel.State) {

        for (i in state.currentStartNewWallpapers until state.wallpapers.size) {
            adapter.addWallpaper(state.wallpapers[i])
        }
    }

    override fun onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            println("OnBackPressed")
            deleteCache(this)
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Press once again to exits", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()

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