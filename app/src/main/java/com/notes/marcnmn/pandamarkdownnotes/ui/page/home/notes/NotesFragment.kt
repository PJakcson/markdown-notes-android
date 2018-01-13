package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.ui.page.BaseFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_notes.*
import javax.inject.Inject

/*
 * Created by marcneumann on 02.01.18.
 */

class NotesFragment : BaseFragment<NotesView, NotesPresenter>(), NotesView {
    @Inject lateinit var adapter: NotesAdapter
    @Inject lateinit var presenter: NotesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }

    override fun render(viewState: NotesViewState) {
        adapter.setNotes(viewState.notes)
        progress.visibility = if (viewState.loading) View.VISIBLE else View.GONE
    }

    override fun createPresenter(): NotesPresenter = presenter

    override fun addNoteIntent(): Observable<Any> = RxView.clicks(add_button)

    override fun noteSelectedIntent(): Observable<Note?> = adapter.selectedSubj

    override fun noteRemovedIntent(): Observable<Note> = adapter.removedSubj.hide()
}