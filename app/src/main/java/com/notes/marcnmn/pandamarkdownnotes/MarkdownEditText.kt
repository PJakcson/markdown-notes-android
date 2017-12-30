package com.notes.marcnmn.pandamarkdownnotes

import android.content.Context
import android.support.design.widget.Snackbar
import android.text.Selection
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.EditText


/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownEditText : EditText {
    private val formatter = MarkdownFormatter(context)
    private val lineBreak = System.getProperty("line.separator")
    private val newLineListeners = mutableListOf<NewLine>()

    constructor(ctx: Context) : super(ctx) {
        init()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    private fun init() {
        this.setBackgroundResource(android.R.color.holo_red_light)
        this.setText("hall *o* was *as*")

        println("parent $parent")
        if (parent != null) {
            val v = parent as View
            println(v.width)
        }

        formatter.formatText(this.text)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (parent != null) width = (parent as View).width
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTextChanged(t: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(t, start, lengthBefore, lengthAfter)
        if (text == null || formatter == null) return
        formatter.formatText(text)
        if (text.endsWith(lineBreak + lineBreak)) createNewLine()
    }

    private fun createNewLine() {
        newLineListeners.forEach { it.newLine() }
        Snackbar.make(this, "Break!!", 1000).show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_DPAD_UP == keyCode && getCurrentCursorLine() == 0) {
            newLineListeners.forEach { it.navigateUp(Selection.getSelectionStart(text)) }
            return false
        }
        if (KeyEvent.KEYCODE_DPAD_DOWN == keyCode && getCurrentCursorLine() == lineCount - 1) {
            newLineListeners.forEach { it.navigateDown(Selection.getSelectionStart(text)) }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun addNewLineListener(nl: NewLine) {
        newLineListeners.add(nl)
    }

    fun getCurrentCursorLine(): Int {
        val selectionStart = Selection.getSelectionStart(text)
        return if (selectionStart != -1) layout.getLineForOffset(selectionStart) else -1
    }
}

interface NewLine {
    fun newLine()
    fun navigateUp(cp: Int)
    fun navigateDown(cp: Int)
}
