package com.swc.sampleapp_mvvm.view.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.swc.sampleapp_mvvm.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel) {
    val weatherData = weatherViewModel.weatherData.collectAsState().value

    Column {
        if (weatherData != null) {
            Text("City: ${weatherData.name}")
            Text("Temperature: ${weatherData.main.temp}°C")
        } else {
            Text("Loading...")
        }
    }
}
