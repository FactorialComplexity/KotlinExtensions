package com.example.kotlinextensions.extensions

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * This function returns [Bitmap] if it's not recycled, otherwise - null.
 *
 * @return [Bitmap] that is not recycled.
 */
fun Bitmap?.getIfNotRecycled(): Bitmap? = this?.takeIf { !it.isRecycled }

/**
 * This function check [Bitmap] is not recycled and recycle it.
 */
fun Bitmap?.checkAndRecycle() = this?.takeIf { !it.isRecycled }?.recycle()

/**
 * This function resize Bitmap [image] according to passed [maxWidth] and [maxHeight].
 *
 * @return resized [Bitmap].
 */
fun Bitmap.resizeImage(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
    val width = image.width
    val height = image.height

    val scaleWidth = maxWidth.toFloat() / width
    val scaleHeight = maxHeight.toFloat() / height

    val matrix = Matrix()

    matrix.postScale(scaleWidth, scaleHeight)

    val resizedBitmap: Bitmap? = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false)

    resizedBitmap?.recycle()

    return resizedBitmap
}
