package com.notes.marcnmn.pandamarkdownnotes.ui.page.write.editor

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import com.notes.marcnmn.pandamarkdownnotes.model.User
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format.MarkdownFormatter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_editor.*
import java.util.*
import javax.inject.Inject

/*
 * Created by marcneumann on 10.01.18.
 */

private val argNote = "note"

class EditorFragment : MviFragment<EditorView, EditorPresenter>(), TextWatcher {
    @Inject lateinit var formatter: MarkdownFormatter
    @Inject lateinit var model: NoteModel
    @Inject lateinit var presenter: EditorPresenter
    @Inject lateinit var user: User
    lateinit var note: Note

    companion object {
        fun newInstance(n: Note): EditorFragment {
            val ef = EditorFragment()
            val b = Bundle()
            b.putParcelable(argNote, n)
            ef.arguments = b
            return ef
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        note = arguments?.getParcelable("note") ?: Note(user, "")
        edit.formatter = formatter
        edit.setText(note.text, TextView.BufferType.EDITABLE)
        edit.addTextChangedListener(this)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun createPresenter(): EditorPresenter = presenter

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s == null) return
        note.text = s.toString()
        note.edited = Calendar.getInstance().time
    }

    override fun onPause() {
        super.onPause()
        model.updateItem(note)
    }
}


class EditorPresenter @Inject constructor(val model: NoteModel) : MviBasePresenter<EditorView, EditorViewState>() {
    override fun bindIntents() {
    }
}

interface EditorView : MvpView

data class EditorViewState(val note: Note)