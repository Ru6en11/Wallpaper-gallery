package com.example.wallpapergallery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.WallpaperModel

class RandomFragmentViewModel : ViewModel() {


    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState() {
        val state = State(ArrayList(), 0)
        stateLiveData.value = state
        getRandomWallpaper()
    }

    fun getRandomWallpaper() {
        val wallpapers = WallpaperModel().getWallpaper()
        val oldState = stateLiveData.value
        val oldCurrent = oldState?.randomWallpapers?.size
        for (wall in wallpapers) {
            oldState?.randomWallpapers?.add(wall)
        }
        oldState?.currentStartNewWallpapers = oldCurrent!!
        stateLiveData.value = oldState!!
    }

    data class State(
        var randomWallpapers: ArrayList<WallpaperModel>,
        var currentStartNewWallpapers: Int
    )
}