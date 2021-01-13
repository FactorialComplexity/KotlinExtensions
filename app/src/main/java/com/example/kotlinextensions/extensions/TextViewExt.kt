package com.example.kotlinextensions.extensions

import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes

/**
 * Returns [String] text.
 *
 * @return [TextView] text as [String].
 */
fun TextView.getStringText(): String = text.toString()

/**
 * Returns trimmed [String] text.
 *
 * @return [TextView] trimmed text as [String].
 */
fun TextView.getTrimmedStringText(): String = getStringText().trim()

/**
 * Sets [TextView] text font. The [id] is resource identifier of font.
 */
fun TextView.setFont(@FontRes id: Int) {
    typeface = context.getFontCompat(id)
}

/**
 * Sets [TextView] text color. The [id] is resource identifier of color.
 */
fun TextView.setTextColorCompat(@ColorRes id: Int) {
    setTextColor(context.getColorCompat(id))
}

/**
 * Sets style span to part of [TextView] text.
 *
 *  [styleableText] is part of text to apply style.
 *  [styleSpan] is span that allows setting the style of the text it's attached to.
 */
fun TextView.setStyleSpan(styleableText: String, styleSpan: StyleSpan) {
    text = SpannableStringBuilder(styleableText).apply {
        setSpan(styleSpan, 0, styleableText.length, 0)
    }
}
