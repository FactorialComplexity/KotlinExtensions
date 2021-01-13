package com.example.kotlinextensions.extensions

import android.net.Uri
import java.io.File

/**
 * This function checks if the file exists and returns the path of [Uri].
 *
 * @return path of file if exists.
 */
fun Uri.getFilePathIfExist(): String? = path?.takeIf { File(it).exists() }
