package com.example.wallpapergallery.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpapergallery.adapters.RecyclerViewWallpaperAdapter
import com.example.wallpapergallery.databinding.FragmentRandomBinding
import com.example.wallpapergallery.listeners.RecyclerViewOnScrollListener
import com.example.wallpapergallery.models.WallpaperModel
import com.example.wallpapergallery.viewmodels.RandomFragmentViewModel


class RandomFragment : Fragment() {

    private lateinit var binding: FragmentRandomBinding

    private val adapter = RecyclerViewWallpaperAdapter()
    private val model: RandomFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomBinding.inflate(inflater, container, false)
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

    private fun renderState(state: RandomFragmentViewModel.State) {

        for (i in state.currentStartNewWallpapers until state.randomWallpapers.size) {
            adapter.addWallpaper(state.randomWallpapers[i])
        }
    }


    private fun initRecyclerView() = with(binding) {
        val layoutManager = GridLayoutManager(activity as AppCompatActivity, 2)
        randomRecyclerView.layoutManager = layoutManager
        randomRecyclerView.adapter = adapter

        //infinityScroll
        randomRecyclerView.addOnScrollListener(RecyclerViewOnScrollListener(layoutManager, model::getRandomWallpaper))
    }

    companion object {

        @JvmStatic fun newInstance() = RandomFragment()

    }
}