package com.example.kotlinextensions.extensions

import android.os.SystemClock
import android.view.View
import android.view.ViewTreeObserver

/**
 * Set [View.OnClickListener] to vararg [views] to listen clicks.
 */
fun View.OnClickListener.setClickListeners(vararg views: View) {
    views.forEach { view -> view.setOnClickListener(this) }
}

/**
 * Set [View.OnClickListener] with [debounceTime] between clicks in milliseconds to vararg [views].
 * Elapsing time is calculated with [SystemClock].
 */
fun View.OnClickListener.setClickListenerWithDebounce(
    vararg views: View,
    debounceTime: Long = 400L
) {
    val clickListener = object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View) {
            SystemClock.elapsedRealtime().takeIf { it - lastClickTime > debounceTime }?.run {
                this@setClickListenerWithDebounce.onClick(v)
                lastClickTime = this
            }
        }
    }

    views.forEach { view -> view.setOnClickListener(clickListener) }
}

/**
 * Set [View.OnClickListener] with [debounceTime] between clicks in milliseconds to View.
 * Elapsing time is calculated with [SystemClock]. If elapsed time less than debounce [action]
 * performing.
 */
fun View.clickWithDebounce(debounceTime: Long = 400L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View) {
            SystemClock.elapsedRealtime().takeIf { it - lastClickTime > debounceTime }?.run {
                action()
                lastClickTime = this
            }
        }
    })
}

/**
 * Set [View.OnFocusChangeListener] to passed vararg [views].
 */
fun View.OnFocusChangeListener.setFocusChangedListeners(vararg views: View) {
    views.forEach { view -> view.onFocusChangeListener = this }
}

/**
 * Remove [View.OnFocusChangeListener] from passed vararg [views].
 */
fun removeFocusChangedListeners(vararg views: View?) {
    views.forEach { view -> view?.onFocusChangeListener = null }
}

/**
 * This function listening global layout changes via [ViewTreeObserver.OnGlobalLayoutListener]
 * and call [block] after View is measured.
 *
 * @param T the type inherited from View.
 */
inline fun <T : View> T.afterMeasured(crossinline block: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            takeIf { measuredWidth > 0 && measuredHeight > 0 }?.let {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                block()
            }
        }
    })
}
