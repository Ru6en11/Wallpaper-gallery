package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpapergallery.adapters.RecyclerViewWallpaperAdapter
import com.example.wallpapergallery.databinding.FragmentRandomBinding
import com.example.wallpapergallery.models.Wallpaper


class RandomFragment : Fragment() {

    private lateinit var binding: FragmentRandomBinding

    private val adapter = RecyclerViewWallpaperAdapter()

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
    }


    private fun initRecyclerView() = with(binding) {
        randomRecyclerView.layoutManager =GridLayoutManager(activity as AppCompatActivity, 2)
        randomRecyclerView.adapter = adapter

        for (i in 1..10) {
            val src = "https://source.unsplash.com/random/1080x1920 ${kotlin.random.Random.nextInt(-10000, 1000)}"
            val wallpaper = Wallpaper(src)
            adapter.addWallpaper(wallpaper)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = RandomFragment()
    }
}