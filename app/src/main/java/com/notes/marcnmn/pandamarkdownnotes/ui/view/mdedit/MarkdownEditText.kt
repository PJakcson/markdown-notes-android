package com.notes.marcnmn.pandamarkdownnotes.ui.view.mdedit

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format.MarkdownFormatter


/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownEditText : EditText {
    var formatter: MarkdownFormatter? = null

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        formatter?.formatText(this.text)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        formatter?.formatText(this.text)
    }
}