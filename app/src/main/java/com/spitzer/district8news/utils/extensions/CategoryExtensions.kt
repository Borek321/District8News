package com.spitzer.district8news.utils.extensions

import com.spitzer.district8news.core.repository.data.Category
import com.spitzer.district8news.utils.values.allowedFilterCategoriesId

fun ArrayList<Category>.filterCategories() =
    this.filter { it -> allowedFilterCategoriesId.contains(it.id) } as ArrayList<Category>