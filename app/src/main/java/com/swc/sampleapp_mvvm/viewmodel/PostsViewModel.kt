package com.swc.sampleapp_mvvm.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swc.sampleapp_mvvm.model.PostResponse
import com.swc.sampleapp_mvvm.model.WeatherResponse
import com.swc.sampleapp_mvvm.repository.PostRepository
import com.swc.sampleapp_mvvm.repository.WeatherRepository
import com.swc.sampleapp_mvvm.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _postsState = MutableStateFlow<UiState<List<PostResponse>>>(UiState.Loading)
    val postsState: StateFlow<UiState<List<PostResponse>>> = _postsState

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                // Get the list of posts from the repository
                val posts = repository.getPosts()
                _postsState.value = UiState.Success(posts)  // Return the list of posts on success
            } catch (e: Exception) {
                _postsState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun onPostClicked(post: PostResponse, index: Int) {
        Log.e("swc", "post ${post.title} clicked, at index $index")
    }
}

