package com.swc.sampleapp_mvvm.network

import com.swc.sampleapp_mvvm.model.Post
import com.swc.sampleapp_mvvm.model.PostResponse
import com.swc.sampleapp_mvvm.model.WeatherResponse
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
    ): List<Post>
}