package com.coolblue.customer.base.presentation.view.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class TextWatcherAdapter internal constructor(
    private val view: EditText,
    private val listener: TextWatcherListener
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        listener.onTextChanged(view, s.toString())
    }

    override fun afterTextChanged(s: Editable) {}
    internal interface TextWatcherListener {
        fun onTextChanged(view: EditText?, text: String?)
    }
}