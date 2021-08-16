package com.spitzer.district8news.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spitzer.district8news.R
import com.spitzer.district8news.core.BaseViewModel
import com.spitzer.district8news.core.Event
import com.spitzer.district8news.core.network.ResultData
import com.spitzer.district8news.core.repository.PostRepository
import com.spitzer.district8news.core.repository.data.Category
import com.spitzer.district8news.core.repository.data.Post
import com.spitzer.district8news.utils.extensions.filterCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PostRepository
) : BaseViewModel() {

    private val _categories = MutableLiveData<ArrayList<Category>>()
    val categories: LiveData<ArrayList<Category>> = _categories

    private val _posts = MutableLiveData<Event<ArrayList<Post>>>()
    val posts: LiveData<Event<ArrayList<Post>>> = _posts

    fun getInitialConfiguration() {
        _loading.value = Event(true)
        viewModelScope.launch {
            fetchInitialData()
        }
    }

    private suspend fun fetchInitialData() {

        val categoriesResult = repository.getCategories()
        val postsResult = repository.getPosts()

        if (categoriesResult is ResultData.Success && postsResult is ResultData.Success) {
            _loading.value = Event(false)
            if (categoriesResult.data == null || postsResult.data == null) {
                _snackbarError.value = Event(R.string.snackbar_could_not_fetch)
            } else {
                _categories.value = categoriesResult.data!!.filterCategories()
                _posts.value = Event(postsResult.data!!)
            }

        } else {
            _loading.value = Event(false)
            if (categoriesResult.isNetworkError() || postsResult.isNetworkError()) {
                _snackbarError.value = Event(R.string.snackbar_network_error)
            } else {
                _snackbarError.value = Event(R.string.snackbar_error)
            }
        }
    }

    fun onPostClicked(postPosition: Int) {

    }
}
