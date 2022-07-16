package com.example.wallpapergallery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wallpapergallery.R
import com.example.wallpapergallery.databinding.WallpaperItemBinding
import com.example.wallpapergallery.models.Wallpaper

class RecyclerViewWallpaperAdapter : RecyclerView.Adapter<RecyclerViewWallpaperAdapter.WallpaperHolder>() {

    private val wallpapersList = ArrayList<Wallpaper>()

    class WallpaperHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = WallpaperItemBinding.bind(item)
        fun bind(wallpaper: Wallpaper) = with(binding) {

            Glide.with(wallpaperItem)
                .load(wallpaper.wallpaperResource)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(wallpaperItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewWallpaperAdapter.WallpaperHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false)
        return WallpaperHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewWallpaperAdapter.WallpaperHolder, position: Int) {
        holder.bind(wallpapersList[position])
    }

    override fun getItemCount(): Int {
        return wallpapersList.size
    }

    fun addWallpaper(wallpaper: Wallpaper) {
        wallpapersList.add(wallpaper)
        notifyDataSetChanged()
    }
}