package com.example.kotlinextensions.extensions

import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Returns [String] text.
 *
 * @return [EditText] text as [String].
 */
fun EditText.getStringText(): String = text.toString()

/**
 * Returns trimmed [String] text.
 *
 * @return [EditText] trimmed text as [String].
 */
fun EditText.getTrimmedStringText(): String = getStringText().trim()

/**
 * This function allows to control [EditText] editing by @see [EditText.disableEditable] and
 * @see [EditText.enableEditable].
 */
fun EditText.setIsEditable(isEditable: Boolean) {
    if (isEditable) {
        enableEditable()
    } else {
        disableEditable()
    }
}

/**
 * This function disabling [EditText] editing by passing setting keyListener to null
 * and enabling focusing in touch mode.
 */
fun EditText.disableEditable() {
    keyListener = null
    isFocusableInTouchMode = false
}

/**
 * This function enabling [EditText] editing by passing [keyListener] and enabling
 * focusing in touch mode.
 */
fun EditText.enableEditable(keyListener: KeyListener? = null) {
    this.keyListener = keyListener
    isFocusableInTouchMode = true
}

/**
 * This function allows to set [text] and [EditText] index of selection.
 * Default selection equal to the length of the passed text.
 */
fun EditText.setTextWithSelection(text: String, selection: Int = text.length) {
    setText(text)
    setSelection(selection)
}

/**
 * This function allows to get text changes in [EditText] with preferred debounce.
 * Under the hood it works with [Job]. [lifecycleOwner] need to control [onChange] emissions.
 * [debounce] is minimal time im milliseconds (default 400) between [onChange] emissions.
 *
 * @return [Job] that performing emissions.
 */
fun EditText.getTextChangesWithDebounce(
    lifecycleOwner: LifecycleOwner,
    debounce: Long = 400L,
    onChange: (CharSequence?) -> Unit
): Job? {
    var textJob: Job? = null
    var lastText = EMPTY_STRING

    val listener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
            Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textJob?.cancel()

            textJob = lifecycleOwner.lifecycleScope.launch {
                text?.let {
                    delay(debounce)

                    it.toString().takeUnless { t -> t.isBlank() || t == lastText }?.let { t ->
                        onChange(t)

                        lastText = t
                    }
                }
            }
        }
    }

    addTextChangedListener(listener)

    return textJob
}

/**
 * This function allows to get text changes in [EditText] with preferred debounce.
 * Under the hood it works with [Flow]. [lifecycleOwner] need to control [onChange] emissions.
 * [debounce] is minimal time im milliseconds (default 400) between [onChange] emissions.
 *
 * @return [Job] of [Flow] handler.
 */
@ExperimentalCoroutinesApi
fun EditText.getFlowTextChanges(
    lifecycleOwner: LifecycleOwner,
    debounce: Long = 400L,
    onChange: (CharSequence?) -> Unit
): Job = callbackFlow<CharSequence?> {
    val listener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
            Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            offer(s)
        }
    }

    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
}.onStart { emit(text) }
    .distinctUntilChanged()
    .filterNot { it.isNullOrBlank() }
    .debounce(debounce)
    .onEach { onChange(it) }
    .launchIn(lifecycleOwner.lifecycleScope)
