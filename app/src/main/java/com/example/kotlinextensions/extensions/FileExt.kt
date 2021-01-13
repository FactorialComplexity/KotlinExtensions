package com.example.kotlinextensions.extensions

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * This function allows to retrieve content [Uri] for passed [File] with instance of [Context] and
 * the [authority] of a [FileProvider] defined in a element in your app's manifest.
 *
 * @return content [Uri] for passed [File].
 */
fun File.getUri(context: Context, authority: String): Uri? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(context, authority, this)
    } else {
        Uri.fromFile(this)
    }
}
