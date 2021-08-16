package com.spitzer.district8news.core.repository

import com.spitzer.district8news.core.network.ResultData
import com.spitzer.district8news.core.network.safeCall
import com.spitzer.district8news.core.repository.data.Category
import com.spitzer.district8news.core.repository.data.Post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val service: PostService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PostRepository {

    override suspend fun getPosts(): ResultData<ArrayList<Post>?> {
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getPosts(CURRENT_PAGE, PAGE_SIZE)
            }
        }
    }

    override suspend fun getPostsByCategories(categories: ArrayList<Int>): ResultData<ArrayList<Post>?> {
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getPostsByCategories(CURRENT_PAGE, PAGE_SIZE, categories)
            }
        }
    }

    override suspend fun getCategories(): ResultData<ArrayList<Category>?> {
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getCategories(PAGE_SIZE)
            }
        }
    }

    companion object {
        const val CURRENT_PAGE = 1
        const val PAGE_SIZE = 10
    }
}