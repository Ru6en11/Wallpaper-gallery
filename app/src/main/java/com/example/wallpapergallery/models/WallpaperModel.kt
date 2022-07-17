package com.example.wallpapergallery.models

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class WallpaperModel (var wallpaperResource: String = "") {

    fun getWallpaper(category: String = ""): ArrayList<WallpaperModel> {
        val wallpaperList = ArrayList<WallpaperModel>()

        val keyword = "?$category"
//        val encodedKeyword =URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())

        for (i in 1..20) {
            val src = "https://source.unsplash.com/random/1080x1920 ${kotlin.random.Random.nextInt(-10000, 1000)} $keyword"
            val wallpaper = WallpaperModel(src)
            wallpaperList.add(wallpaper)
        }

        return wallpaperList
    }
}