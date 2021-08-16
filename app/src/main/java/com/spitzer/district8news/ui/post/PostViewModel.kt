package com.spitzer.district8news.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spitzer.district8news.core.BaseViewModel
import com.spitzer.district8news.core.Event
import com.spitzer.district8news.core.navigation.NavigationCommand
import com.spitzer.district8news.core.repository.data.Post

class PostViewModel : BaseViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _viewState = MutableLiveData<Event<Boolean>>()
    val viewState: LiveData<Event<Boolean>> = _viewState

    fun setPostModel(model: Post) {
        _loading.value = Event(true)
        _post.value = model
        // TODO: define states to give ui responsiveness
        _viewState.value = Event(true)
    }

    fun viewLoaded() {
        _loading.value = Event(false)
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }
}