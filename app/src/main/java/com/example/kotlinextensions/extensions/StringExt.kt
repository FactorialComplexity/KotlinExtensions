package com.example.kotlinextensions.extensions

import android.net.Uri
import android.util.Base64
import android.webkit.URLUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.regex.Pattern

const val EMPTY_STRING = ""
const val SPACE_STRING = " "
const val EMAIL_PATTERN = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
const val LATIN_PATTERN = ".*[A-Za-z].*"

/**
 * Property of [String.Companion] that returns empty string, see [EMPTY_STRING].
 */
val String.Companion.EMPTY: String
    get() = EMPTY_STRING

/**
 * Property of [String] that returns md5 hash of String.
 */
val String.md5: String
    get() = MessageDigest.getInstance("MD5").digest(toByteArray()).joinToString(String.EMPTY) {
        "%02x".format(it)
    }

/**
 * Property of [String] that returns is String contains latin letters.
 */
val String.containsLatinLetter: Boolean
    get() = matches(LATIN_PATTERN.toRegex())

/**
 * Property of [String] that returns is String contains digits.
 */
val String.containsDigit: Boolean
    get() = any { it.isDigit() }

/**
 * Property of [String] that returns is String contains latin letters and digits.
 */
val String.hasLettersAndDigits: Boolean
    get() = containsLatinLetter && containsDigit

/**
 * Property of [String] that returns last path component with considering slashes/backslashes in path.
 */
val String.lastPathComponent: String
    get() {
        var path = this
        if (path.endsWith("/"))
            path = path.substring(0, path.length - 1)
        var index = path.lastIndexOf('/')
        if (index < 0) {
            if (path.endsWith("\\"))
                path = path.substring(0, path.length - 1)
            index = path.lastIndexOf('\\')
            if (index < 0)
                return path
        }
        return path.substring(index + 1)
    }

/**
 * Creates a [Uri] which parses the given encoded URI string.
 *
 * @return parsed [Uri] or null if couldn't parse string.
 */
fun String.toUri(): Uri? {
    return try {
        if (URLUtil.isValidUrl(this))
            Uri.parse(this)
        else
            null
    } catch (e: Exception) {
        null
    }
}

/**
 * Gets the words from String with preferred [delimiter].
 *
 * @return list of words of passed String.
 */
fun String.toWords(delimiter: String = SPACE_STRING): List<String> = split(delimiter)

/**
 * Encodes String to [Base64] with preferred encoding [flag].
 *
 * @return Base64-encoded String.
 */
fun String.toBase64(flag: Int = Base64.NO_WRAP): String = String(Base64.encode(toByteArray(), flag))

/**
 * Decodes [Base64] to String with preferred decoding [flag].
 *
 * @return String decoded from Base54.
 */
fun String.fromBase64(flag: Int = Base64.NO_WRAP): String = String(Base64.decode(this, flag))

/**
 * Checks if email valid with [Pattern].
 *
 * @return is email valid.
 */
fun String.isEmailValid(): Boolean =
    Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE)
        .matcher(this)
        .matches()

/**
 * Converts String to [JSONObject].
 *
 * @return [JSONObject] converted from String.
 */
fun String.toJsonObject(): JSONObject? {
    return try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }
}

/**
 * Converts [JSONObject] to String.
 *
 * @return String converted from [JSONObject].
 */
fun String.toJsonArray(): JSONArray? {
    return try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }
}
