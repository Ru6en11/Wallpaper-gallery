package com.example.wallpapergallery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.WallpaperModel

class RandomFragmentViewModel : ViewModel() {

    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState() {
        val state = State(ArrayList())
        stateLiveData.value = state
        getRandomWallpaper()
    }

    fun getRandomWallpaper() {
        stateLiveData.value?.randomWallpapers = WallpaperModel().getWallpaper()
    }

    data class State(
        var randomWallpapers: ArrayList<WallpaperModel>
    )
}