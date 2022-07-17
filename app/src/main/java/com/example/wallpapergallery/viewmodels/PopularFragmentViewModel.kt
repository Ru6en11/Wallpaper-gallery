package com.example.wallpapergallery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.WallpaperModel

class PopularFragmentViewModel : ViewModel() {

    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState() {
        val state = State(ArrayList(), 0)
        stateLiveData.value = state
        getPopularWallpaper()
    }

    fun getPopularWallpaper() {
        val wallpapers = WallpaperModel().getWallpaper()
        val oldState = stateLiveData.value
        val oldCurrent = oldState?.popularWallpapers?.size
        for (wall in wallpapers) {
            oldState?.popularWallpapers?.add(wall)
        }
        oldState?.currentStartNewWallpapers = oldCurrent!!
        stateLiveData.value = oldState!!
    }

    data class State(
        var popularWallpapers: ArrayList<WallpaperModel>,
        var currentStartNewWallpapers: Int
    )

    companion object {
        const val PARAM = "popular"
    }
}