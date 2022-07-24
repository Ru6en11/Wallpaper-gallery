package com.example.wallpapergallery.listeners

import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpapergallery.viewmodels.MainFragmentViewModel
import kotlin.properties.Delegates

class RecyclerViewWallpaperOnScrollListener(
    private val manager: GridLayoutManager,
    val fetchData: (category: String) -> Unit,
    val category: String = "",
    var view: RelativeLayout? = null
) : RecyclerView.OnScrollListener() {

    private var loading = true
    private var pastVisibleItems by Delegates.notNull<Int>()
    private var visibleItemCount by Delegates.notNull<Int>()
    private var totalItemCount by Delegates.notNull<Int>()

    final val state = mutableListOf<Int>(1)

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        state[0] = newState
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0 && (state[0] == 0 || state[0] == 2)) {
            hideView()
        } else if (dy < -10) {
            showView()
        }
        if (dy > 0) {
            visibleItemCount = manager.childCount
            totalItemCount = manager.itemCount
            pastVisibleItems = manager.findFirstVisibleItemPosition()

            if (loading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 2) {
                    loading = false
                    fetchData(category)
                    loading = true
                }
            }
        }
    }

    private fun hideView() {
        view?.visibility = View.GONE
    }

    private fun showView() {
        view?.visibility = View.VISIBLE
    }


}