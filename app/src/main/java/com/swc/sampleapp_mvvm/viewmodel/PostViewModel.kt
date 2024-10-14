package com.swc.sampleapp_mvvm.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swc.sampleapp_mvvm.model.Post
import com.swc.sampleapp_mvvm.model.PostResponse
import com.swc.sampleapp_mvvm.model.WeatherResponse
import com.swc.sampleapp_mvvm.repository.PostRepository
import com.swc.sampleapp_mvvm.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _postData = MutableStateFlow<List<Post>?>(null)
    val postData: StateFlow<List<Post>?> = _postData

    fun fetchPosts() {
        Log.e("swc", "fetch posts")
        viewModelScope.launch {
            try {
                Log.e("swc", "fetch posts launch");

                val response = repository.getPosts()
                _postData.value = response
                Log.e("swc", "fetch posts resp: $response");

            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
