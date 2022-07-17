package com.example.wallpapergallery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.WallpaperModel

class PopularFragmentViewModel : ViewModel() {

    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState() {
        val state = State(ArrayList())
        stateLiveData.value = state
        getPopularWallpaper()
    }

    fun getPopularWallpaper() {
        val param: String = "popular"
        stateLiveData.value?.popularWallpapers = WallpaperModel().getWallpaper(param)
    }

    data class State(
        var popularWallpapers: ArrayList<WallpaperModel>
    )
}