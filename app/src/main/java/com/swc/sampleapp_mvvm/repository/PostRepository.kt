package com.swc.sampleapp_mvvm.repository


import com.swc.sampleapp_mvvm.model.local.PostDao
import com.swc.sampleapp_mvvm.model.local.PostEntity
import com.swc.sampleapp_mvvm.model.local.toEntityList
import com.swc.sampleapp_mvvm.model.remote.PostResponse
import com.swc.sampleapp_mvvm.network.PostApiService
import com.swc.sampleapp_mvvm.network.TimeoutException
import com.swc.sampleapp_mvvm.network.TimeoutType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
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
//    suspend fun getPosts(): List<PostResponse> {
//        // Try to fetch from the database first
//        val cachedPosts = postDao.getAllPostsOnce()
//
//        // If there are no posts in the DB, fetch from the API
//        return if (cachedPosts.isEmpty()) {
//            val apiPosts = apiService.getPosts()
//
//            // Cache the API result in the DB
//            postDao.insertPosts(apiPosts.map { post ->
//                PostEntity(post.id, 0,  post.title, post.body)
//            })
//
//            apiPosts
//        } else {
//            // Map the cached posts from DB to PostResponse
//            cachedPosts.map { postEntity ->
//                PostResponse(0, postEntity.id, postEntity.title, postEntity.body)
//            }
//        }
//    }
//    suspend fun getCachedPosts(): List<PostResponse> {
//        val cachedPosts = postDao.getAllPostsOnce()
//        return cachedPosts.map { postEntity ->
//            PostResponse(0, postEntity.id, postEntity.title, postEntity.body)
//        }
//    }

//    suspend fun getPosts(): List<PostResponse> {
//        val apiPosts = apiService.getPosts()
////        postDao.insertPosts(apiPosts.map { post ->
////            PostEntity(post.id, 0, post.title, post.body)
////        })
//        insertIfChanged(apiPosts.toEntityList())
//        return apiPosts
//    }

    /**
     * Fetch posts from the API and cache them locally. Returns a Result wrapping the list of posts.
     */
    suspend fun getPosts(): Result<List<PostResponse>> {
        return try {
            val apiPosts = apiService.getPosts()
            insertIfChanged(apiPosts.toEntityList())
            Result.success(apiPosts)
        } catch (e: SocketTimeoutException) {
            // Catch timeout exception and throw custom exception
//            throw TimeoutException("Request timed out", TimeoutType.CONNECTION_TIMEOUT)
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetch posts from the local cache. Returns a Result wrapping the list of cached posts.
     */
    suspend fun getCachedPosts(): Result<List<PostResponse>> {
        return try {
            val cachedPosts = postDao.getAllPostsOnce()
            val postResponses = cachedPosts.map { postEntity ->
                PostResponse(0, postEntity.id, postEntity.title, postEntity.body)
            }
            Result.success(postResponses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Insert new posts into the local database if they have changed.
     */
    private suspend fun insertIfChanged(newPosts: List<PostEntity>) {
        val existingPosts = postDao.getAllPostsOnce() // Assuming this fetches all posts
        val postsToInsert = newPosts.filter { newPost ->
            existingPosts.none { it.id == newPost.id && it == newPost }
        }
        if (postsToInsert.isNotEmpty()) {
            postDao.insertPosts(postsToInsert)
        }
    }
}

