package com.example.kotlinextensions.extensions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File
import java.lang.Exception

/**
 * This function retrieves [Bitmap] from [ImageView] Drawable.
 *
 * @return [Bitmap] retrieved from ImageView Drawable.
 */
fun ImageView.getBitmap(): Bitmap? = (drawable as? BitmapDrawable)?.bitmap

/**
 * This function allows to load resource ([String], [Uri], [Bitmap], [Drawable], [Bitmap])
 * into given [ImageView]. [DiskCacheStrategy] can be selected by [cacheStrategy],
 * which [DiskCacheStrategy.ALL] by default.
 * Set error resource by [errorRes] or [errorDrawable].
 * Set placeholder resource by [placeholderRes] or [placeholderDrawable].
 * Pass [ProgressBar] with visibility [View.VISIBLE] or [View.GONE] to show
 * for progress visualization.
 * The [transformations] allows to transform loaded resource. By default [CenterCrop].
 * [onSuccess] callback emits successfully loaded resource as [Drawable].
 * [onError] callback emits [GlideException] if loading resource failed.
 */
fun ImageView.loadImage(
    url: String = "",
    uri: Uri? = null,
    file: File? = null,
    @DrawableRes drawableRes: Int? = null,
    bitmap: Bitmap? = null,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
    @DrawableRes errorRes: Int? = null,
    errorDrawable: Drawable? = null,
    @DrawableRes placeholderRes: Int? = null,
    placeholderDrawable: Drawable? = null,
    progressView: ProgressBar? = null,
    transformations: List<BitmapTransformation> = listOf(CenterCrop()),
    onSuccess: (Drawable?) -> Unit,
    onError: (Exception?) -> Unit
) {
    progressView?.isVisible = true

    val requestManager = Glide.with(this)

    val requestBuilder = when {
        uri != null -> requestManager.load(uri)
        file != null -> requestManager.load(file)
        drawableRes != null -> requestManager.load(drawableRes)
        bitmap != null -> requestManager.load(bitmap)
        else -> requestManager.load(url)
    }

    requestBuilder.apply {
        diskCacheStrategy(cacheStrategy)

        placeholderRes?.let { placeholder(it) } ?: placeholderDrawable?.let { placeholder(it) }
        errorRes?.let { error(it) } ?: errorDrawable?.let { error(it) }

        transform(*transformations.toTypedArray())

        listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressView?.isVisible = false
                onSuccess(resource)

                return true
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressView?.isVisible = false
                onError(e)

                return true
            }
        })
    }.into(this)
}
