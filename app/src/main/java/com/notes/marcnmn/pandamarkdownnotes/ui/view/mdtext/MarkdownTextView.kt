package com.notes.marcnmn.pandamarkdownnotes.ui.view.mdtext

import android.content.Context
import android.text.Spannable
import android.util.AttributeSet
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format.MarkdownFormatter


/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownTextView : TextView {
    var formatter: MarkdownFormatter? = null

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    fun setMarkdownText(text: CharSequence) {
        super.setText(text, BufferType.SPANNABLE)
        formatter?.formatText(this.text as Spannable)
    }
}