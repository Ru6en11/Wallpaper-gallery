package com.example.wallpapergallery.viewmodels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.WallpaperModel
import kotlinx.android.parcel.Parcelize

class MainFragmentViewModel : ViewModel() {


    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    val searchState: LiveData<SearchState> get() = searchStateLiveData
    private val searchStateLiveData = MutableLiveData<SearchState>()

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

    fun initSearchState(searchState: SearchState) {
        searchStateLiveData.value = searchState
    }

    fun switchIsSearchVisible(visible: Boolean) {
        val oldState = searchStateLiveData.value
        searchStateLiveData.value = oldState?.copy(
            visible = visible
        )
    }

    fun setSearchQueryParams(query: String) {
        val oldState = searchState.value
        searchStateLiveData.value = oldState?.copy(
            query = query
        )
    }

    data class State(
        var wallpapers: ArrayList<WallpaperModel>,
        var currentStartNewWallpapers: Int
    )

    @Parcelize
    data class SearchState (
        val visible: Boolean,
        val query: String
    ): Parcelable
}