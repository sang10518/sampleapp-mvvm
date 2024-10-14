package com.swc.sampleapp_mvvm.model

data class PostResponse(
    val data: List<Post>
)

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)