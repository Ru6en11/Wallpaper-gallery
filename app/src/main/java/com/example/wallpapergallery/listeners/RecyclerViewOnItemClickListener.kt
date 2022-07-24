package com.example.wallpapergallery.listeners

import com.example.wallpapergallery.models.WallpaperModel

interface RecyclerViewOnItemClickListener {

    fun onClickRecyclerViewItem(wallpaper: WallpaperModel)
}