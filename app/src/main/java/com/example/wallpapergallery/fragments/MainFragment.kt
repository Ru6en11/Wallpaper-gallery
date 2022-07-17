package com.example.wallpapergallery.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpapergallery.R
import com.example.wallpapergallery.adapters.RecyclerViewSearchAdapter
import com.example.wallpapergallery.adapters.ViewPagerAdapter
import com.example.wallpapergallery.databinding.FragmentMainBinding
import com.example.wallpapergallery.listeners.RecyclerViewOnScrollListener
import com.example.wallpapergallery.viewmodels.MainFragmentViewModel
import com.example.wallpapergallery.viewmodels.RandomFragmentViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val model: MainFragmentViewModel by activityViewModels()
    private val adapter = RecyclerViewSearchAdapter()
    private lateinit var layoutManager: GridLayoutManager

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

        //fixme
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (model.searchState.value == null) {
            model.initSearchState(
                MainFragmentViewModel.SearchState(
                    visible = false,
                    query = ""
            ))
        }

        model.searchState.observe(viewLifecycleOwner) {
            searchHolder.visibility = if (it.visible) View.VISIBLE else View.GONE
        }

        //Задаем адаптер для ViewPager2
        val adapter = ViewPagerAdapter(activity as AppCompatActivity, fragmentsList)
        binding.viewPager2.adapter = adapter

        //Устанавливаем default tab
        binding.viewPager2.currentItem = 1

        //Синхронизация работы TabLayout и ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, pos ->
            tab.text =fragmentListTittle[pos]
        }.attach()

        layoutManager = GridLayoutManager(activity as AppCompatActivity, 2)
        initSearchRecyclerView()

        if (model.state.value == null) {
            model.initState()
        }

        model.state.observe(viewLifecycleOwner) {
            renderState(it)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("SAVE ${model.searchState.value?.visible}")
        outState.putParcelable(KEY_SEARCH_STATE, model.searchState.value)
    }

    private fun renderSearchState(searchState: MainFragmentViewModel.SearchState) = with(binding){

        searchHolder.visibility = if (searchState.visible) View.VISIBLE else View.GONE
    }

    //Подключаем toolbar_menu к toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        //Подключаем searchView
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))

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
                searchRecyclerView.addOnScrollListener(RecyclerViewOnScrollListener(layoutManager,
                    model::searchWallpaper, query))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })

        //fixme
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


    companion object {
        @JvmStatic fun newInstance() = MainFragment()
        @JvmStatic private val KEY_SEARCH_STATE = "KEY_SEARCH_STATE"
    }
}