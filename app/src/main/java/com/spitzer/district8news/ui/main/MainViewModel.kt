package com.spitzer.district8news.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spitzer.district8news.R
import com.spitzer.district8news.core.BaseViewModel
import com.spitzer.district8news.core.Event
import com.spitzer.district8news.core.navigation.NavigationCommand
import com.spitzer.district8news.core.network.ResultData
import com.spitzer.district8news.core.repository.PostRepository
import com.spitzer.district8news.core.repository.data.Post
import com.spitzer.district8news.ui.categoryfilteradapter.CategoryModel
import com.spitzer.district8news.utils.extensions.filterCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PostRepository
) : BaseViewModel() {

    private val _categories = MutableLiveData<ArrayList<CategoryModel>>()
    val categories: LiveData<ArrayList<CategoryModel>> = _categories

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
                val filteredCategories = categoriesResult.data!!.filterCategories()
                _categories.value = filteredCategories.mapIndexed { index, cat ->
                    CategoryModel(
                        cat,
                        false,
                        index == filteredCategories.size - 1
                    )
                } as ArrayList<CategoryModel>
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

    fun getPostsByCategories() {
        _loading.value = Event(true)
        viewModelScope.launch {
            fetchPostsByCategories()
        }
    }

    private suspend fun fetchPostsByCategories() {
        val selectedCategories = categories.value!!.filter { it.isSelected }
        val postsResult:  ResultData<ArrayList<Post>?> = if (selectedCategories.isEmpty()) {
            repository.getPosts()
        } else {
            repository.getPostsByCategories(
                selectedCategories.map { it.category.id } as ArrayList<Int>
            )
        }

        if (postsResult is ResultData.Success) {
            _loading.value = Event(false)
            if (postsResult.data == null) {
                _snackbarError.value = Event(R.string.snackbar_could_not_fetch)
            } else {
                _posts.value = Event(postsResult.data!!)
            }
        } else {
            _loading.value = Event(false)
            if (postsResult.isNetworkError()) {
                _snackbarError.value = Event(R.string.snackbar_network_error)
            } else {
                _snackbarError.value = Event(R.string.snackbar_error)
            }
        }
    }

    fun onPostClicked(postClicked: Post) {
        val action = MainFragmentDirections
            .actionMainFragmentToPostFragment(
                postClicked
            )
        _navigation.value = Event(NavigationCommand.To(action))
    }

    fun toggleCategorySelection(categoryIndex: Int) {
        _categories.value!![categoryIndex].isSelected =
            !_categories.value!![categoryIndex].isSelected
    }
}
