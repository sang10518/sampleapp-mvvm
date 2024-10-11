package com.swc.sampleapp_mvvm.view

import com.swc.sampleapp_mvvm.viewmodel.WeatherViewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.swc.sampleapp_mvvm.view.custom.PostScreen
import com.swc.sampleapp_mvvm.view.custom.WeatherScreen
import com.swc.sampleapp_mvvm.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            val weatherViewModel: WeatherViewModel = viewModel()
//            // Call fetchWeather with your API key
//            weatherViewModel.fetchWeather("London", "abc123456def7890ghijklmnopqrs") // Replace with your API key
//            WeatherScreen(weatherViewModel)
            val postViewModel: PostViewModel = viewModel()
            // Call fetchWeather with your API key
            postViewModel.fetchPosts() // Replace with your API key
            PostScreen(postViewModel)
        }


    }
}
