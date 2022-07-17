package com.example.wallpapergallery.models

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class CategoryModel(
    var wallpaperResource: String = "",
    var title: String = ""
) {

    fun getCategory(categoryTitlesList: Array<String>): ArrayList<CategoryModel> {

        val categoryList = ArrayList<CategoryModel>()

        for (title in categoryTitlesList) {
            val keyword = title
            var encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
            val src = "https://source.unsplash.com/random/1080x1920? ${kotlin.random.Random.nextInt(-10000, 1000)} $encodedKeyword"
            val category = CategoryModel(src, title)
            categoryList.add(category)
        }

        return categoryList
    }

}
