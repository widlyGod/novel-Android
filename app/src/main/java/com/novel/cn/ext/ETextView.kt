package com.novel.cn.ext

import android.service.autofill.TextValueSanitizer
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

inline fun TextView.textWatcher(body: ETextWatcher.() -> Unit) = addTextChangedListener(ETextWatcher().apply(body))

val TextView.isEmpty
    get() = text.isEmpty()

val TextView.isNotEmpty
    get() = text.isNotEmpty()

val TextView.isBlank
    get() = text.isBlank()

val TextView.isNotBlank
    get() = text.isNotBlank()

class ETextWatcher : TextWatcher {


    private var _afterTextChanged: ((Editable?) -> Unit)? = null

    private var _beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    private var _onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    override fun afterTextChanged(s: Editable?) {
        _afterTextChanged?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s, start, before, count)
    }


    fun afterTextChanged(listener: (Editable?) -> Unit) {
        this._afterTextChanged = listener
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        this._beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
        this._onTextChanged = listener
    }
}