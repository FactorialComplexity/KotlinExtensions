package com.example.kotlinextensions.extensions

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/**
 * Set Soft Input Mode [WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING].
 */
fun Activity.setAdjustNothing() =
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

/**
 * Set Soft Input Mode [WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE].
 */
fun Activity.setAdjustResize() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
    } else {
        @Suppress("DEPRECATION")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}

/**
 * Set Soft Input Mode [WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN].
 */
fun Activity.setAdjustPan() =
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

/**
 * Set full screen by hiding [WindowInsets.Type.statusBars()] on API version 30 or greater.
 * If API version is less than 30 full screen sets by flags
 * [WindowManager.LayoutParams.FLAG_FULLSCREEN] and [WindowManager.LayoutParams.FLAG_FULLSCREEN].
 */
fun Activity.setFullScreen() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

/**
 * Set requested orientation [ActivityInfo.SCREEN_ORIENTATION_SENSOR]. Would like to have the
 * screen in landscape orientation, but if the user has enabled sensor-based rotation then
 * we can use the sensor to change which direction the screen is facing.
 */
fun Activity.setRotatable() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

/**
 * Set requested orientation [ActivityInfo.SCREEN_ORIENTATION_PORTRAIT]. Would like to have the
 * screen in portrait orientation, but if the user has enabled sensor-based rotation then
 * we can use the sensor to change which direction the screen is facing.
 */
fun Activity.setOnlyPortraitMode() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

/**
 * Set requested orientation [ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE]. Would like to have the
 * screen in portrait orientation, but can use the sensor to change which direction the screen is facing.
 */
fun Activity.setOnlyLandscapeMode() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

/**
 * Register for Activity result callback which can be launched by .launch(Intent) function
 * @see [ActivityResultLauncher]. [onResultOk] callback invokes when Activity For Result
 * returned value and success code. Otherwise will be invoked [onCancel] callback.
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Activity.registerForActivityResultCallback(
    onResultOk: (data: Intent?) -> Unit,
    onCancel: () -> Unit
): ActivityResultLauncher<Intent>? =
    (this as? AppCompatActivity)?.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            onResultOk(it.data)
        } else {
            onCancel()
        }
    }

/**
 * Register request permission callback which can be launched by .launch(String) function
 * @see [ActivityResultLauncher]. [onResult] callback invokes when user manage requested permission.
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Activity.registerRequestPermissionCallback(onResult: (result: Boolean) -> Unit): ActivityResultLauncher<String>? =
    (this as? AppCompatActivity)?.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        onResult(it)
    }

/**
 * Register request permissions callback which can be launched by .launch(vararg String) function
 * @see [ActivityResultLauncher]. Pass [onResultEach] or [onResultAll] callbacks for returning
 * each permissions results or [Boolean] result for all.
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Activity.registerRequestMultiplePermissionsCallback(
    onResultEach: (resultsMap: Map<String, Boolean>) -> Unit = {},
    onResultAll: (result: Boolean) -> Unit
): ActivityResultLauncher<Array<out String>>? =
    (this as? AppCompatActivity)?.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        onResultEach(it)
        onResultAll(it.values.all { value -> value })
    }

/**
 * Register taking picture callback which can be launched by .launch(Uri) function
 * @see [ActivityResultLauncher]. [onResult] callback emits is picture stored or not.
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Activity.registerForTakingPictureCallback(onResult: (isSaved: Boolean) -> Unit): ActivityResultLauncher<Uri>? =
    (this as? AppCompatActivity)?.registerForActivityResult(ActivityResultContracts.TakePicture()) {
        onResult(it)
    }

/**
 * Register taking video callback which can be launched by .launch(Uri) function
 * @see [ActivityResultLauncher]. [onResult] callback emits [Bitmap] thumbnail of video if it
 * present.
 *
 * @return [ActivityResultLauncher] -A launcher for a previously prepared call.
 */
fun Activity.registerForTakingVideoCallback(onResult: (thumbnail: Bitmap) -> Unit): ActivityResultLauncher<Uri>? =
    (this as? AppCompatActivity)?.registerForActivityResult(ActivityResultContracts.TakeVideo()) {
        onResult(it)
    }
