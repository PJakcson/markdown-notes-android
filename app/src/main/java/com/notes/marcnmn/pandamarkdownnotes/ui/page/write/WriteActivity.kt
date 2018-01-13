package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.os.Bundle
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import com.notes.marcnmn.pandamarkdownnotes.model.User
import com.notes.marcnmn.pandamarkdownnotes.ui.page.BaseActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.editor.EditorFragment
import javax.inject.Inject

class WriteActivity : BaseActivity() {
    @Inject lateinit var model: NoteModel
    @Inject lateinit var user: User

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        note = intent?.getParcelableExtra("note") ?: Note(user, "")
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditorFragment.newInstance(note))
                .commit()
    }
}
