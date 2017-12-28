package com.notes.marcnmn.pandamarkdownnotes

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText


/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownEditText : EditText, TextWatcher {
    private val formatter = MarkdownFormatter(context)

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.setBackgroundResource(android.R.color.transparent)

        this.addTextChangedListener(this)
        this.setText("hall *o* was *as*")
        formatter.formatText(this.text)
    }

    @Suppress("RedundantOverride")
    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        if (s != null) formatter.formatText(this.text)
    }
}
