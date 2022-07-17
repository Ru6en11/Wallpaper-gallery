package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpapergallery.R
import com.example.wallpapergallery.adapters.RecyclerViewCategoryAdapter
import com.example.wallpapergallery.databinding.FragmentCategoryBinding
import com.example.wallpapergallery.models.Category
import com.example.wallpapergallery.viewmodels.CategoryFragmentViewModel
import com.example.wallpapergallery.viewmodels.RandomFragmentViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
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