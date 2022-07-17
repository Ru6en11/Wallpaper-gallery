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
//        for (wall in state.randomWallpapers) {
//            adapter.addWallpaper(wall)
//        }
    }


    private fun initRecyclerView() = with(binding) {
        val layoutManager = GridLayoutManager(activity as AppCompatActivity, 2)
        randomRecyclerView.layoutManager = layoutManager
        randomRecyclerView.adapter = adapter
//        initScrollListener(layoutManager, model::getRandomWallpaper)

        randomRecyclerView.addOnScrollListener(RecyclerViewOnScrollListener(layoutManager, model::getRandomWallpaper))
    }

    private fun initScrollListener(manager: GridLayoutManager, f: () -> Unit) = with(binding){
        var loading = true
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int


        randomRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = manager.childCount
                    totalItemCount = manager.itemCount
                    pastVisibleItems = manager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 1) {
                            loading = false
                            println("LAST ITEM")
                            fun f() = model.getRandomWallpaper()
                            loading = true
                        }
                    }
                }
            }
        })
    }

    companion object {

        @JvmStatic fun newInstance() = RandomFragment()

    }
}