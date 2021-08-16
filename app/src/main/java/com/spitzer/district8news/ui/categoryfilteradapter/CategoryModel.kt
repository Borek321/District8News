package com.spitzer.district8news.ui.categoryfilteradapter

import com.spitzer.district8news.core.repository.data.Category

class CategoryModel(
    val category: Category,
    var isSelected: Boolean = false,
    var isLastItem: Boolean = false
)