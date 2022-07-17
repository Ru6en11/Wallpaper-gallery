package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpapergallery.adapters.RecyclerViewWallpaperAdapter
import com.example.wallpapergallery.databinding.FragmentPopularBinding
import com.example.wallpapergallery.models.WallpaperModel


class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val adapter = RecyclerViewWallpaperAdapter()

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
    }


    private fun initRecyclerView() = with(binding) {
        popularRecyclerView.layoutManager = GridLayoutManager(activity as AppCompatActivity, 2)
        popularRecyclerView.adapter = adapter

        for (i in 1..10) {
            val src = "https://source.unsplash.com/random/1080x1920?popular ${kotlin.random.Random.nextInt(-10000, 1000)}"
            val wallpaper = WallpaperModel(src)
            adapter.addWallpaper(wallpaper)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = PopularFragment()
    }
}