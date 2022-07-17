package com.example.wallpapergallery.listeners

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class RecyclerViewOnScrollListener (
    private val manager: GridLayoutManager,
    val fetchData: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var loading = true
    private var pastVisibleItems by Delegates.notNull<Int>()
    private var visibleItemCount by Delegates.notNull<Int>()
    private var totalItemCount by Delegates.notNull<Int>()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            visibleItemCount = manager.childCount
            totalItemCount = manager.itemCount
            pastVisibleItems = manager.findFirstVisibleItemPosition()

            if (loading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 2) {
                    loading = false
                    fetchData()
                    loading = true
                }
            }
        }
    }


}