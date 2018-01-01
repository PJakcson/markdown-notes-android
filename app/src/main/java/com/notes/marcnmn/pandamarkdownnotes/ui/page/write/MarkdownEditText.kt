package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.MarkdownFormatter


/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownEditText : EditText {
    private var formatter: MarkdownFormatter? = null
        set(value) {
            field = value
        }

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    private fun init() {
        if (parent != null) {
            val v = parent as View
            println(v.width)
        }
        format()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (parent != null) width = (parent as View).width
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTextChanged(t: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(t, start, lengthBefore, lengthAfter)
        format()
    }

    private fun format() {
        if (text != null) formatter?.formatText(text)
    }
}