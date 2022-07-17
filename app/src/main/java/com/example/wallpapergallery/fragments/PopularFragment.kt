package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpapergallery.adapters.RecyclerViewWallpaperAdapter
import com.example.wallpapergallery.databinding.FragmentPopularBinding
import com.example.wallpapergallery.listeners.RecyclerViewOnScrollListener
import com.example.wallpapergallery.models.WallpaperModel
import com.example.wallpapergallery.viewmodels.PopularFragmentViewModel
import com.example.wallpapergallery.viewmodels.RandomFragmentViewModel


class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val adapter = RecyclerViewWallpaperAdapter()
    private val model: PopularFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        if (model.state.value == null) {
            model.initState()
        }

        model.state.observe(viewLifecycleOwner) {
            renderState(it)
        }

    }

    private fun renderState(state: PopularFragmentViewModel.State) {

        for (i in state.currentStartNewWallpapers until state.popularWallpapers.size) {
            adapter.addWallpaper(state.popularWallpapers[i])
        }
    }


    private fun initRecyclerView() = with(binding) {
        val layoutManager = GridLayoutManager(activity as AppCompatActivity, 2)
        popularRecyclerView.layoutManager = layoutManager
        popularRecyclerView.adapter = adapter

        //infinityScroll
        popularRecyclerView.addOnScrollListener(RecyclerViewOnScrollListener(layoutManager, model::getPopularWallpaper))
    }

    companion object {

        @JvmStatic
        fun newInstance() = PopularFragment()
    }
}