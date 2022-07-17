package com.example.wallpapergallery.models

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Wallpaper (var wallpaperResource: String = "") {

    fun getWallpaper(category: String = ""): ArrayList<Wallpaper> {
        val wallpaperList = ArrayList<Wallpaper>()

        val keyword = "?$category"
        val encodedKeyword =URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())

        for (i in 1..10) {
            val src = "https://source.unsplash.com/random/1080x1920$encodedKeyword ${kotlin.random.Random.nextInt(-10000, 1000)}"
            val wallpaper = Wallpaper(src)
            wallpaperList.add(wallpaper)
        }

        return wallpaperList
    }
}