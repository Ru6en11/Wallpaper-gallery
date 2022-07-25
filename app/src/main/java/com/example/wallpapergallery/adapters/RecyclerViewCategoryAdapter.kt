package com.example.wallpapergallery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wallpapergallery.R
import com.example.wallpapergallery.databinding.CategoryItemBinding
import com.example.wallpapergallery.listeners.RecyclerViewCategoryOnItemClickListener
import com.example.wallpapergallery.listeners.RecyclerViewWallpaperOnItemClickListener
import com.example.wallpapergallery.models.CategoryModel

class RecyclerViewCategoryAdapter(val clickListener: RecyclerViewCategoryOnItemClickListener) : RecyclerView.Adapter<RecyclerViewCategoryAdapter.CategoryHolder>() {

    private val categoryList = ArrayList<CategoryModel>()

    class CategoryHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemBinding.bind(item)
        fun bind(category: CategoryModel, clickListener: RecyclerViewCategoryOnItemClickListener) = with(binding) {

            Glide.with(categoryImageView)
                .load(category.wallpaperResource)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(categoryImageView)

            categoryTextView.text = category.title

            itemCategory.setOnClickListener { clickListener.onClickRecyclerViewItem(category) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewCategoryAdapter.CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return RecyclerViewCategoryAdapter.CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewCategoryAdapter.CategoryHolder, position: Int) {
        holder.bind(categoryList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun addWallpaper(categoryModel: CategoryModel) {
        categoryList.add(categoryModel)
        notifyDataSetChanged()
    }
}