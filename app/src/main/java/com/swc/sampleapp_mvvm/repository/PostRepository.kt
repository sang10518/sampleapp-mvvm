package com.swc.sampleapp_mvvm.repository


import com.swc.sampleapp_mvvm.model.local.PostDao
import com.swc.sampleapp_mvvm.model.local.PostEntity
import com.swc.sampleapp_mvvm.model.remote.PostResponse
import com.swc.sampleapp_mvvm.network.PostApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class PostRepository @Inject constructor(
//    private val apiService: PostApiService, // For network calls
//    private val postDao: PostDao // For local database
//) {
//
//    // Backing StateFlow to emit and cache posts
//    private val _postsFlow = MutableStateFlow<List<PostEntity>>(emptyList())
//    val postsFlow: StateFlow<List<PostEntity>> = _postsFlow.asStateFlow()
//
//    // Function to fetch posts
//    suspend fun fetchPosts() {
//        try {
//            // Fetch posts from API
//            val remotePosts = apiService.getPosts()
//
//            // Map API response to Room entity
//            val postEntities = remotePosts.map { it.toEntity() }
//
//            // Cache posts locally in Room
//            postDao.clearPosts()
//            postDao.insertPosts(postEntities)
//
//            // Update StateFlow
//            _postsFlow.value = postEntities
//        } catch (e: Exception) {
//            // Handle error (e.g., log it or show a message)
//        }
//    }
//
//    // Function to load posts from local database
//    suspend fun loadPostsFromCache() {
//        _postsFlow.value = postDao.getAllPostsOnce()
//    }
//
//    // Helper to map PostResponse to PostEntity
//    private fun PostResponse.toEntity(): PostEntity {
//        return PostEntity(id = id, title = title, body = body, userId = useId)
//    }
//}

//@Singleton
//class PostRepository @Inject constructor(
//    private val apiService: PostApiService
//) {
//    suspend fun getPosts(): List<PostResponse> {
//        return apiService.getPosts()  // This should return a List<PostResponse>
//    }
//}

@Singleton
class PostRepository @Inject constructor(
    private val apiService: PostApiService,
    private val postDao: PostDao
) {
    suspend fun getPosts(): List<PostResponse> {
        // Try to fetch from the database first
        val cachedPosts = postDao.getAllPostsOnce()

        // If there are no posts in the DB, fetch from the API
        return if (cachedPosts.isEmpty()) {
            val apiPosts = apiService.getPosts()

            // Cache the API result in the DB
            postDao.insertPosts(apiPosts.map { post ->
                PostEntity(post.id, 0,  post.title, post.body)
            })

            apiPosts
        } else {
            // Map the cached posts from DB to PostResponse
            cachedPosts.map { postEntity ->
                PostResponse(0, postEntity.id, postEntity.title, postEntity.body)
            }
        }
    }
}

