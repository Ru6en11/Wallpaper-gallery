package com.example.wallpapergallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpapergallery.adapters.RecyclerViewWallpaperAdapter
import com.example.wallpapergallery.databinding.FragmentRandomBinding
import com.example.wallpapergallery.viewmodels.RandomFragmentViewModel


class RandomFragment : Fragment() {

    private lateinit var binding: FragmentRandomBinding

    private val adapter = RecyclerViewWallpaperAdapter()
    private val model: RandomFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        if (model.state.value == null) {
            model.initState()
        }

        model.state.observe(viewLifecycleOwner) {
            renderState(it)
        }

    }

    private fun renderState(state: RandomFragmentViewModel.State) {
        for (i in state.randomWallpapers.indices) {
            adapter.addWallpaper(state.randomWallpapers[i])
        }
    }


    private fun initRecyclerView() = with(binding) {
        randomRecyclerView.layoutManager =GridLayoutManager(activity as AppCompatActivity, 2)
        randomRecyclerView.adapter = adapter
    }

    companion object {

        @JvmStatic fun newInstance() = RandomFragment()
        @JvmStatic private val KEY_STATE = "STATE"
    }
}