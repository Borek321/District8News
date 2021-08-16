package com.spitzer.district8news.core.repository

import com.spitzer.district8news.core.repository.data.Category
import com.spitzer.district8news.core.repository.data.Post
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface PostService {
    @POST("posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<ArrayList<Post>>

    @POST("posts")
    suspend fun getPostsByCategories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("categories") categories: List<Int>,
    ): Response<ArrayList<Post>>

    @POST("categories")
    suspend fun getCategories(
        @Query("per_page") perPage: Int
    ): Response<ArrayList<Category>>
}
