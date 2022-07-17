package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpapergallery.MainActivity
import com.example.wallpapergallery.R
import com.example.wallpapergallery.adapters.RecyclerViewCategoryAdapter
import com.example.wallpapergallery.databinding.FragmentCategoryBinding
import com.example.wallpapergallery.listeners.RecyclerViewOnScrollListener
import com.example.wallpapergallery.viewmodels.CategoryFragmentViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var ma: RelativeLayout
    private val adapter = RecyclerViewCategoryAdapter()
    private val model: CategoryFragmentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ma = (activity as MainActivity).mainToolbarLayout
        initRecyclerView()

        if (model.state.value == null) {
            val categoryTitlesList: Array<String> = resources.getStringArray(R.array.categoryTitles)
            model.initState()
            model.getCategory(categoryTitlesList)
        }

        model.state.observe(viewLifecycleOwner) {
            renderState(it)
        }
    }


    private fun initRecyclerView() = with(binding) {
        categoryRecyclerView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        categoryRecyclerView.adapter = adapter

        //infinityScroll
        categoryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            final val state = mutableListOf<Int>(1)

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                state[0] = newState
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (state[0] == 0 || state[0] == 2)) {
                    ma.visibility = View.GONE
                } else if (dy < -10) {
                    ma.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun renderState(state: CategoryFragmentViewModel.State) {
        for (category in state.categoryItems) {
            adapter.addWallpaper(category)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = CategoryFragment()
    }
}