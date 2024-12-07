package com.swc.sampleapp_mvvm

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.android.HiltAndroidApp

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val diskCacheSizeBytes = 1024 * 1024 * 100L // 100 MB
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(context, "cacheFolderName", diskCacheSizeBytes)
        )
    }
}
