package com.example.wallpapergallery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpapergallery.models.Category
import com.example.wallpapergallery.models.Wallpaper

class CategoryFragmentViewModel : ViewModel() {

    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState() {
        val state = State(ArrayList())
        stateLiveData.value = state
    }

    fun getCategory(categoryTitlesList: Array<String>) {
        stateLiveData.value?.categoryItems = Category().getCategory(categoryTitlesList)
    }

    data class State(
        var categoryItems: ArrayList<Category>
    )
}