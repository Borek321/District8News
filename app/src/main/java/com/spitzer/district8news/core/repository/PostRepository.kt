package com.spitzer.district8news.core.repository

import com.spitzer.district8news.core.network.ResultData
import com.spitzer.district8news.core.repository.data.Category
import com.spitzer.district8news.core.repository.data.Post

interface PostRepository {
    suspend fun getPosts(): ResultData<ArrayList<Post>?>
    suspend fun getPostsByCategories(categories: ArrayList<Int>): ResultData<ArrayList<Post>?>
    suspend fun getCategories(): ResultData<ArrayList<Category>?>
}
