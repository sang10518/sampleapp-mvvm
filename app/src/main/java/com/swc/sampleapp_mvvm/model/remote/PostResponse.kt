package com.swc.sampleapp_mvvm.model.remote


data class PostResponse(
    val useId: Int,
    val id: Int,
    val title: String,
    val body: String
)
