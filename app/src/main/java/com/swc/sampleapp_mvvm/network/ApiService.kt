package com.swc.sampleapp_mvvm.network

import com.swc.sampleapp_mvvm.model.remote.PostResponse
import com.swc.sampleapp_mvvm.model.remote.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(
    ): List<PostResponse>
}