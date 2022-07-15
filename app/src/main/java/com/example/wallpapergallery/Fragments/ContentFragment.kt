package com.example.wallpapergallery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallpapergallery.R
import com.example.wallpapergallery.databinding.FragmentContentBinding


class ContentFragment : Fragment() {

    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentFragment()
    }
}