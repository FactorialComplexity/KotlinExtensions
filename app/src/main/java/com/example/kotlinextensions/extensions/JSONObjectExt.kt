package com.example.kotlinextensions.extensions

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * This function tries to get Int value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [Int] retrieved from [JSONObject].
 */
fun JSONObject.getIntOrNull(name: String): Int? =
    wrapGetter(onTry = { getInt(name) }, onCatch = { getStringOrNull(name)?.toIntOrNull() })

/**
 * This function tries to get Double value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [Double] retrieved from [JSONObject].
 */
fun JSONObject.getDoubleOrNull(name: String): Double? = wrapGetter(onTry = { getDouble(name) })

/**
 * This function tries to get Long value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [Long] retrieved from [JSONObject].
 */
fun JSONObject.getLongOrNull(name: String): Long? = wrapGetter(onTry = { getLong(name) })

/**
 * This function tries to get String value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [String] retrieved from [JSONObject].
 */
fun JSONObject.getStringOrNull(name: String): String? =
    wrapGetter(onTry = { getString(name).trim() })

/**
 * This function tries to get Boolean value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [Boolean] retrieved from [JSONObject].
 */
fun JSONObject.getBooleanOrNull(name: String): Boolean? = wrapGetter(onTry = { getBoolean(name) })

/**
 * This function tries to get Object value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [JSONArray] retrieved from [JSONObject].
 */
fun JSONObject.getObjectOrNull(name: String): JSONObject? =
    wrapGetter(onTry = { getJSONObject(name) })

/**
 * This function tries to get [JSONArray] value from [JSONObject] or null. Wrapped in [wrapGetter].
 *
 * @return [JSONArray] retrieved from [JSONObject].
 */
fun JSONObject.getArrayOrNull(name: String): JSONArray? = wrapGetter(onTry = { getJSONArray(name) })

/**
 * This function tries to get [JSONArray] value from [JSONObject] or empty [JSONArray].
 * Wrapped in [wrapGetter].
 *
 * @return [JSONArray] retrieved from [JSONObject].
 */
fun JSONObject.getArrayOrEmpty(name: String): JSONArray? =
    wrapGetter(onTry = { getJSONArray(name) }, onCatch = { JSONArray() })

/**
 * This function wrapping invocation callback [onTry] into try-catch block
 * and return value if callback invocation is success, otherwise - [onCatch] callback
 * will be invoked.
 *
 * @param T type of getting value.
 *
 * @return value of type [T].
 */
private fun <T> wrapGetter(onTry: () -> T?, onCatch: () -> T? = { null }): T? {
    return try {
        onTry()
    } catch (e: JSONException) {
        onCatch()
    }
}
