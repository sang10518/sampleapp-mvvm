package com.swc.sampleapp_mvvm.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swc.sampleapp_mvvm.model.remote.PostResponse
import com.swc.sampleapp_mvvm.repository.PostRepository
import com.swc.sampleapp_mvvm.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

//@HiltViewModel
//class PostsViewModel @Inject constructor(
//    private val repository: PostRepository
//) : ViewModel() {
//
//    private val _postsState = MutableStateFlow<UiState<List<PostResponse>>>(UiState.Loading)
//    val postsState: StateFlow<UiState<List<PostResponse>>> = _postsState
//
//    fun fetchPosts() {
//        viewModelScope.launch {
//            try {
//                // Get the list of posts from the repository
//                val posts = repository.getPosts()
//                _postsState.value = UiState.Success(posts)  // Return the list of posts on success
//            } catch (e: Exception) {
//                _postsState.value = UiState.Error(e.message ?: "An unknown error occurred")
//            }
//        }
//    }
//
//    fun onPostClicked(post: PostResponse, index: Int) {
//        Log.e("swc", "post ${post.title} clicked, at index $index")
//    }
//}

//@HiltViewModel
//class PostsViewModel @Inject constructor(
//    private val repository: PostRepository
//) : ViewModel() {
//
//    private val _postsState = MutableStateFlow<UiState<List<PostResponse>>>(UiState.Loading)
//    val postsState: StateFlow<UiState<List<PostResponse>>> = _postsState
//
//    fun fetchPosts() {
//        viewModelScope.launch(Dispatchers.IO) {
//            // Step 1: Load cached posts first and emit them to the UI
//            val cachedPosts = repository.getCachedPosts()  // Assuming this method fetches posts from the DB
//            if (cachedPosts.isNotEmpty()) {
//                withContext(Dispatchers.Main) {
//                    _postsState.value = UiState.Success(cachedPosts)
//                }
//            }
//
//            // Step 2: Fetch fresh data from the API and emit the updated posts
//            try {
//                val apiPosts = repository.getPosts()
//                withContext(Dispatchers.Main) {
//                    _postsState.value = UiState.Success(apiPosts)  // Emit fresh data from the API
//                }
//
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    _postsState.value = UiState.Error(e.message ?: "An unknown error occurred")
//                }
//            }
//        }
//    }
//
//    fun onPostClicked(post: PostResponse, index: Int) {
//        Log.e("swc", "post ${post.title} clicked, at index $index")
//    }
//}

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _postsState = MutableStateFlow<UiState<List<PostResponse>>>(UiState.Loading)
    val postsState: StateFlow<UiState<List<PostResponse>>> = _postsState

    /**
     * Fetch posts by first loading cached posts and then updating with fresh data from the API.
     */
    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            // Step 1: Load cached posts first and emit them to the UI
            repository.getCachedPosts().fold(
                onSuccess = { cachedPosts ->
                    if (cachedPosts.isNotEmpty()) {
                        _postsState.value = UiState.Success(cachedPosts)
//                        _postsState.emit(UiState.Success(cachedPosts))
                    }
                },
                onFailure = { exception ->
                    _postsState.value = (UiState.Error(exception.localizedMessage ?: "Error loading cached posts"))
                }
            )

            // Step 2: Fetch fresh data from the API and emit the updated posts
            repository.getPosts().fold(
                onSuccess = { apiPosts ->
                    _postsState.value = (UiState.Success(apiPosts))
                },
                onFailure = { exception ->
                    _postsState.value = (UiState.Error(exception.localizedMessage ?: "Error fetching posts from API"))
                }
            )
        }
    }

    /**
     * Handle post click events.
     */
    fun onPostClicked(post: PostResponse, index: Int) {
        Log.e("swc", "post ${post.title} clicked, at index $index")
    }
}

