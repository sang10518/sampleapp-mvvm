package com.swc.sampleapp_mvvm.view

import com.swc.sampleapp_mvvm.viewmodel.WeatherViewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.swc.sampleapp_mvvm.R
import com.swc.sampleapp_mvvm.model.remote.PostResponse
import com.swc.sampleapp_mvvm.ui.UiState
import com.swc.sampleapp_mvvm.view.custom.PostsScreen
import com.swc.sampleapp_mvvm.view.custom.WeatherScreen
import com.swc.sampleapp_mvvm.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postsViewModel: PostsViewModel by viewModels()
        postsViewModel.fetchPosts()
        lifecycleScope.launch {
            postsViewModel.postsState.collect {post ->
                val a: UiState<List<PostResponse>> = post

                when (a) {
                    is UiState.Loading -> {
                        //loading
                    }
                    is UiState.Success -> {
                        //do smth with a.data
                    }

                    is UiState.Error -> {
                        //error...
                    }
                }
                //adapter.updateItems(post)
            }
        }

        setContent {
//            val weatherViewModel: WeatherViewModel = viewModel()
//            // Call fetchWeather with your API key
//            weatherViewModel.fetchWeather("London", "abc123456def7890ghijklmnopqrs") // Replace with your API key
//            WeatherScreen(weatherViewModel)
//            val postsViewModel: PostsViewModel = viewModel()
//            // Call fetchWeather with your API key
//            postsViewModel.fetchPosts() // Replace with your API key
            PostsScreen(postsViewModel)
        }

//        setContentView(R.layout.activity_main)


    }
}
