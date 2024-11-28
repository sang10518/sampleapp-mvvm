package com.swc.sampleapp_mvvm.repository


import com.swc.sampleapp_mvvm.model.PostResponse
import com.swc.sampleapp_mvvm.network.PostApiService
import com.swc.sampleapp_mvvm.network.WeatherApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val apiService: PostApiService
) {
    suspend fun getPosts(): List<PostResponse> {
        return apiService.getPosts()  // This should return a List<PostResponse>
    }
}
