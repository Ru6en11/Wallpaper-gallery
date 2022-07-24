package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpapergallery.MainActivity
import com.example.wallpapergallery.adapters.RecyclerViewWallpaperAdapter
import com.example.wallpapergallery.databinding.FragmentRandomBinding
import com.example.wallpapergallery.listeners.RecyclerViewOnItemClickListener
import com.example.wallpapergallery.listeners.RecyclerViewWallpaperOnScrollListener
import com.example.wallpapergallery.models.WallpaperModel
import com.example.wallpapergallery.viewmodels.RandomFragmentViewModel


class RandomFragment : Fragment(), RecyclerViewOnItemClickListener {

    private lateinit var binding: FragmentRandomBinding
    private lateinit var ma: RelativeLayout
    private val adapter = RecyclerViewWallpaperAdapter(this)
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

        ma = (activity as MainActivity).mainToolbarLayout

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
        randomRecyclerView.addOnScrollListener(RecyclerViewWallpaperOnScrollListener(layoutManager, model::getRandomWallpaper, view = ma))
    }

    override fun onClickRecyclerViewItem(wallpaper: WallpaperModel) {
        Toast.makeText(activity as AppCompatActivity, "Click", Toast.LENGTH_SHORT).show()
    }

    companion object {

        @JvmStatic fun newInstance() = RandomFragment()
    }
}