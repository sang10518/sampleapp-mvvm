package com.swc.sampleapp_mvvm.repository


import com.swc.sampleapp_mvvm.network.WeatherApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getWeather(city: String, apiKey: String) = weatherApiService.getWeather(city, apiKey)
}
