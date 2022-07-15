package com.example.wallpapergallery.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import com.example.wallpapergallery.R
import com.example.wallpapergallery.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        initToolbar()
        initSidebarItemSelectedListener()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Подключаем toolbar_menu к toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        //Подключаем searchView
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))

        //Слушатель для searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //todo
                Toast.makeText(activity as AppCompatActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //todo
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
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
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        //Смена шрифта у tittle
        toolbar.setTitleTextAppearance(activity as AppCompatActivity, R.style.toolbarStyle)
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

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}