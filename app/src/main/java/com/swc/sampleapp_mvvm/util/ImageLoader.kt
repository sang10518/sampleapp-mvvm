package com.swc.sampleapp_mvvm.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ImageLoader private constructor(
    private val context: Context,
    private val imageUrl: String?,
    private val placeholder: Int?,
    private val errorDrawable: Int?,
    private val scaleType: ScaleType
) {
    enum class ScaleType {
        CENTER_CROP, FIT_CENTER, CENTER_INSIDE
    }

    class Builder(private val context: Context) {
        private var imageUrl: String? = null
        private var placeholder: Int? = null
        private var errorDrawable: Int? = null
        private var scaleType: ScaleType = ScaleType.CENTER_CROP

        fun setImageUrl(url: String?): Builder {
            this.imageUrl = url
            return this
        }

        fun setPlaceholder(resourceId: Int?): Builder {
            this.placeholder = resourceId
            return this
        }

        fun setErrorDrawable(resourceId: Int?): Builder {
            this.errorDrawable = resourceId
            return this
        }

        fun setScaleType(scaleType: ScaleType): Builder {
            this.scaleType = scaleType
            return this
        }

        fun build(): ImageLoader {
            return ImageLoader(context, imageUrl, placeholder, errorDrawable, scaleType)
        }
    }

    fun loadInto(imageView: ImageView) {
        val requestOptions = RequestOptions().apply {
            when (scaleType) {
                ScaleType.CENTER_CROP -> centerCrop()
                ScaleType.FIT_CENTER -> fitCenter()
                ScaleType.CENTER_INSIDE -> centerInside()
            }
            placeholder?.let { placeholder(it) }
            errorDrawable?.let { error(it) }
        }

        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}
