package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpapergallery.R
import com.example.wallpapergallery.adapters.RecyclerViewCategoryAdapter
import com.example.wallpapergallery.databinding.FragmentCategoryBinding
import com.example.wallpapergallery.models.Category



class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val adapter = RecyclerViewCategoryAdapter()


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
    }


    private fun initRecyclerView() = with(binding) {
        categoryRecyclerView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        categoryRecyclerView.adapter = adapter
        val categoryTitlesList: Array<String> = resources.getStringArray(R.array.categoryTitles)

        for (i in categoryTitlesList) {
            val src = "https://source.unsplash.com/random/1080x1920? ${kotlin.random.Random.nextInt(-1000, 1000)} $i"
            val category = Category(src, i)
            adapter.addWallpaper(category)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = CategoryFragment()
    }
}