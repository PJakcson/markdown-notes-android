package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Fade
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import com.notes.marcnmn.pandamarkdownnotes.ui.page.BaseActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format.MarkdownFormatter
import kotlinx.android.synthetic.main.activity_write.*
import java.util.*
import javax.inject.Inject

class WriteActivity : BaseActivity(), TextWatcher {
    @Inject lateinit var formatter: MarkdownFormatter
    @Inject lateinit var model: NoteModel

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        window.enterTransition = Fade()

        if (intent == null) return

        note = intent.getParcelableExtra("note")
        edit.setText(note.text, TextView.BufferType.EDITABLE)
        edit.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
        if (s == null) return
        formatter.formatText(s)
        note.text = s.toString()
        note.edited = Calendar.getInstance().time
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun onPause() {
        super.onPause()
        model.updateItem(note)
    }
}
