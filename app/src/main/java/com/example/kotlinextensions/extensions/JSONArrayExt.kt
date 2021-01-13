package com.example.kotlinextensions.extensions

import org.json.JSONArray

/**
 * This function mapping [JSONArray] with type [T] to [MutableList] with type [R]
 * by invocation [transform] callback.
 *
 * @param T type of initial [JSONArray].
 * @param R type of mapped [MutableList].
 *
 * @return [MutableList] mapped from [JSONArray].
 */
inline fun <reified T, R> JSONArray.map(transform: (T?) -> R): MutableList<R?> =
    mapTo(mutableListOf(), transform)

/**
 * This function mapping [JSONArray] with type [T] to [MutableList] with type [R]
 * by invocation [transform] callback.
 *
 * @param T type of initial [JSONArray].
 * @param R type of mapped [MutableList].
 *
 * @return [MutableList] mapped from [JSONArray].
 */
inline fun <reified T, R> JSONArray.mapTo(
    to: MutableList<R?>,
    transform: (T?) -> R
): MutableList<R?> = (0..length()).mapTo(to, { i -> transform(get(i) as? T) })

/**
 * This function mapping [JSONArray] non-null values with type [T] to [MutableList] with type [R]
 * by invocation [transform] callback.
 *
 * @param T type of initial [JSONArray].
 * @param R type of mapped [MutableList].
 *
 * @return [MutableList] mapped from [JSONArray].
 */
inline fun <reified T, R> JSONArray.mapNotNull(transform: (T) -> R): MutableList<R> =
    mapNotNullTo(mutableListOf(), transform)

/**
 * This function mapping [JSONArray] non-null values with type [T] to [MutableList] with type [R]
 * by invocation [transform] callback.
 *
 * @param T type of initial [JSONArray].
 * @param R type of mapped [MutableList].
 *
 * @return [MutableList] mapped from [JSONArray].
 */
inline fun <reified T, R> JSONArray.mapNotNullTo(
    to: MutableList<R>,
    transform: (T) -> R
): MutableList<R> =
    to.apply {
        (0..length()).forEach { i -> (get(i) as? T)?.let { add(transform(it)) } }
    }

/**
 * This function mapping [JSONArray] indexed values with type [T] to [MutableList] with type [R]
 * by invocation [transform] callback.
 *
 * @param T type of initial [JSONArray].
 * @param R type of mapped [MutableList].
 *
 * @return [MutableList] mapped from [JSONArray].
 */
inline fun <reified T, R> JSONArray.mapIndexed(transform: (index: Int, T?) -> R): MutableList<R?> =
    mapIndexedTo(mutableListOf(), transform)

/**
 * This function mapping [JSONArray] indexed values with type [T] to [MutableList] with type [R]
 * by invocation [transform] callback.
 *
 * @param T type of initial [JSONArray].
 * @param R type of mapped [MutableList].
 *
 * @return [MutableList] mapped from [JSONArray].
 */
inline fun <reified T, R> JSONArray.mapIndexedTo(
    to: MutableList<R?>,
    transform: (index: Int, T?) -> R?
): MutableList<R?> =
    (0..length()).mapTo(to, { i -> transform(i, get(i) as? T) })

/**
 * This function emitting values of [JSONArray] with type [T] to [action] callback.
 *
 * @param T type of initial [JSONArray].
 */
inline fun <reified T> JSONArray.forEach(action: (T?) -> Unit) =
    (0..length()).forEach { action(get(it) as? T) }

/**
 * This function emitting indexed values of [JSONArray] with type [T] to [action] callback.
 *
 * @param T type of initial [JSONArray].
 */
inline fun <reified T> JSONArray.forEachIndexed(action: (index: Int, T?) -> Unit) =
    (0..length()).forEach { action(it, get(it) as? T) }

/**
 * This function emitting non-null values of [JSONArray] with type [T] to [action] callback.
 *
 * @param T type of initial [JSONArray].
 */
inline fun <reified T> JSONArray.forEachNotNull(action: (T) -> Unit) =
    (0..length()).forEach { action(get(it) as T) }
