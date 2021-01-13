package com.example.kotlinextensions.extensions

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

/**
 * @see [Activity.registerForActivityResultCallback].
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Fragment.registerForActivityResultCallback(
    onResultOk: (data: Intent?) -> Unit,
    onCancel: () -> Unit
): ActivityResultLauncher<Intent>? =
    activity?.registerForActivityResultCallback(onResultOk, onCancel)

/**
 * @see [Activity.registerRequestPermissionCallback].
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Fragment.registerRequestPermissionCallback(onResult: (result: Boolean) -> Unit): ActivityResultLauncher<String>? =
    activity?.registerRequestPermissionCallback(onResult)

/**
 * @see [Activity.registerRequestMultiplePermissionsCallback].
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Fragment.registerRequestMultiplePermissionsCallback(
    onResultEach: (resultsMap: Map<String, Boolean>) -> Unit = {},
    onResultAll: (result: Boolean) -> Unit
): ActivityResultLauncher<Array<out String>>? =
    activity?.registerRequestMultiplePermissionsCallback(onResultEach, onResultAll)

/**
 * @see [Activity.registerForTakingPictureCallback].
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Fragment.registerForTakingPictureCallback(onResult: (isSaved: Boolean) -> Unit): ActivityResultLauncher<Uri>? =
    activity?.registerForTakingPictureCallback(onResult)

/**
 * @see [Activity.registerForTakingVideoCallback].
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Fragment.registerForTakingVideoCallback(onResult: (thumbnail: Bitmap) -> Unit): ActivityResultLauncher<Uri>? =
    activity?.registerForTakingVideoCallback(onResult)
