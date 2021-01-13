package com.example.kotlinextensions.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

/**
 * Property of [Context] that returns your app version name [String].
 */
val Context.versionName: String?
    get() = try {
        val pInfo = packageManager.getPackageInfo(packageName, 0);
        pInfo?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }

/**
 * Property of [Context] that returns your app version code [Long].
 */
val Context.versionCode: Long?
    get() = try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            pInfo?.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            pInfo?.versionCode?.toLong()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }

/**
 * This function allows to retrieve preferred color resource with [ContextCompat].
 * [id] - identifier of color resource.
 *
 * @return [Int] color resource.
 */
fun Context.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

/**
 * This function allows to retrieve preferred Drawable resource with [ContextCompat].
 * [id] - identifier of Drawable resource.
 *
 * @return [Drawable] resource.
 */
fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)

/**
 * This function allows to retrieve preferred Typeface resource with [ContextCompat].
 * [id] - identifier of Typeface resource.
 *
 * @return [Typeface] resource.
 */
fun Context.getFontCompat(@FontRes id: Int): Typeface? = ResourcesCompat.getFont(this, id)
