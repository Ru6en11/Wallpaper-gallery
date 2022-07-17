package com.example.wallpapergallery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wallpapergallery.R
import com.example.wallpapergallery.databinding.CategoryItemBinding
import com.example.wallpapergallery.models.CategoryModel

class RecyclerViewCategoryAdapter : RecyclerView.Adapter<RecyclerViewCategoryAdapter.CategoryHolder>() {

    private val categoryList = ArrayList<CategoryModel>()

    class CategoryHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemBinding.bind(item)
        fun bind(categoryModel: CategoryModel) = with(binding) {

            Glide.with(categoryImageView)
                .load(categoryModel.wallpaperResource)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(categoryImageView)

            categoryTextView.text = categoryModel.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewCategoryAdapter.CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return RecyclerViewCategoryAdapter.CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewCategoryAdapter.CategoryHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun addWallpaper(categoryModel: CategoryModel) {
        categoryList.add(categoryModel)
        notifyDataSetChanged()
    }
}