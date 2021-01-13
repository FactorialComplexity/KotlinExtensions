package com.example.kotlinextensions.extensions

import java.lang.ref.WeakReference

/**
 * This function returns [WeakReference] of preferred object with type [T].
 *
 * @param T type of object.
 *
 * @return [WeakReference] of passed object.
 */
val <T> T.weak: WeakReference<T>
    get() = WeakReference(this)

/**
 * This function allows to invoke [block] if [predicate] callback satisfies the condition.
 *
 * @return result of predicate invocation.
 */
fun <T> T.applyIf(predicate: () -> Boolean, block: T.() -> Unit): Boolean {
    val result = predicate()

    if (result) {
        block.invoke(this)
    }

    return result
}

/**
 * This function allows to apply [let] function with 2 arguments.
 *
 * @param T1 - type of first param for let function.
 * @param T2 - type of second param for let function.
 * @param R - type of return value.
 *
 * @return result of [block] invocation.
 */
fun <T1 : Any, T2 : Any, R : Any> let(
    first: T1? = null,
    second: T2? = null,
    block: (T1, T2) -> R?
): R? = first?.let { f ->
    second?.let { block(f, it) }
}

/**
 * This function allows to apply [let] function with 3 arguments.
 *
 * @param T1 - type of first param for let function.
 * @param T2 - type of second param for let function.
 * @param T3 - type of third param for let function.
 * @param R - type of return value.
 *
 * @return result of [block] invocation.
 */
fun <T1 : Any, T2 : Any, T3 : Any, R : Any> let(
    first: T1? = null,
    second: T2? = null,
    third: T3? = null,
    block: (T1, T2, T3) -> R?
): R? = let(first, second) { f, s ->
    third?.let { block(f, s, third) }
}

/**
 * This function allows to apply [let] function with 4 arguments.
 *
 * @param T1 - type of first param for let function.
 * @param T2 - type of second param for let function.
 * @param T3 - type of third param for let function.
 * @param T3 - type of fourth param for let function.
 * @param R - type of return value.
 *
 * @return result of [block] invocation.
 */
fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> let(
    first: T1? = null,
    second: T2? = null,
    third: T3? = null,
    fourth: T4? = null,
    block: (T1, T2, T3, T4) -> R?
): R? = let(first, second, third) { f, s, t ->
    fourth?.let { block(f, s, t, it) }
}
