package com.notes.marcnmn.pandamarkdownnotes.ui.page.write.editor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/*
 * Created by marcneumann on 10.01.18.
 */

class EditorFragment : MviFragment<EditorView, EditorPresenter>() {
    @Inject lateinit var presenter: EditorPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun createPresenter(): EditorPresenter {
        return presenter
    }
}


class EditorPresenter @Inject constructor(val model: NoteModel) : MviBasePresenter<EditorView, EditorViewState>() {
    override fun bindIntents() {
    }
}

interface EditorView : MvpView

data class EditorViewState(val note: Note)