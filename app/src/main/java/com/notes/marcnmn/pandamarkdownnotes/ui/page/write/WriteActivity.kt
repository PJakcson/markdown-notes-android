package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.ui.page.BaseActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format.MarkdownFormatter
import javax.inject.Inject

class WriteActivity : BaseActivity(), TextWatcher {
    @Inject lateinit var formatter: MarkdownFormatter

    private lateinit var editText: MarkdownEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        editText = findViewById(R.id.edit)
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
        if (s != null) formatter.formatText(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
