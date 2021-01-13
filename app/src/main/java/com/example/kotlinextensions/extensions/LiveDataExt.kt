package com.example.kotlinextensions.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Observing Live Data and invokes [callback] when non-null value emitted.
 * [lifecycleOwner] need to control the observer.
 *
 * @param T type of [LiveData] value.
 */
fun <T> LiveData<T?>.safeObserve(
    lifecycleOwner: LifecycleOwner,
    callback: (value: T) -> Unit
) = observe(lifecycleOwner) {
    it?.let { callback(it) }
}

/**
 * Observing Live Data and invokes [callback] when value emitted.
 * [lifecycleOwner] need to control the observer.
 * After [callback] invoked observer will be removed.
 *
 * @param T type of [LiveData] value.
 */
fun <T> MutableLiveData<T?>.observeOnce(
    lifecycleOwner: LifecycleOwner,
    callback: (value: T?) -> Unit
) {
    var observer: Observer<T?>? = null

    observer = Observer<T?> {
        callback(it)

        observer?.let { o -> removeObserver(o) }
    }

    observe(lifecycleOwner, observer)
}

/**
 * Observing Live Data and invokes [callback] when value non-null emitted.
 * [lifecycleOwner] need to control the observer.
 * After [callback] invoked observer will be removed.
 *
 * @param T type of [LiveData] value.
 */
fun <T> MutableLiveData<T?>.safeObserveOnce(
    lifecycleOwner: LifecycleOwner,
    callback: (value: T) -> Unit
) {
    var observer: Observer<T?>? = null

    observer = Observer<T?> {
        it?.let { v ->
            callback(v)

            observer?.let { o ->
                removeObserver(o)
            }
        }
    }

    observe(lifecycleOwner, observer)
}
