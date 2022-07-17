package com.example.wallpapergallery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.WallpaperModel

class MainFragmentViewModel : ViewModel() {


    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState() {
        val state = State(ArrayList(), 0)
        stateLiveData.value = state
    }

    fun searchWallpaper(searchWord: String) {
        println("!!!!!!!!$searchWord!!!!!!")
        val wallpapers = WallpaperModel().getWallpaper(searchWord)
        val oldState = stateLiveData.value
        val oldCurrent = oldState?.wallpapers?.size
        for (wall in wallpapers) {
            oldState?.wallpapers?.add(wall)
        }
        oldState?.currentStartNewWallpapers = oldCurrent!!
        stateLiveData.value = oldState!!
    }

    fun clearState() {
        val oldState = stateLiveData.value
        oldState?.wallpapers?.clear()
        stateLiveData.value = State(ArrayList(), 0)
    }

    data class State(
        var wallpapers: ArrayList<WallpaperModel>,
        var currentStartNewWallpapers: Int
    )
}