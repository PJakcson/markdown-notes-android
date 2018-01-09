package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/*
 * Created by marcneumann on 09.01.18.
 */

class NotesPresenter @Inject constructor(val model: NoteModel) : MviBasePresenter<NotesView, NotesViewState>() {
    private val disposable = CompositeDisposable()

    override fun bindIntents() {
        disposable.addAll(intent { it.addNoteIntent() }.map(model::addItem).subscribe())
        val no = model.notes.map { NotesViewState.showResultState(it) }
                .startWith(NotesViewState.showLoadingState())
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(no, NotesView::render)
    }

    override fun unbindIntents() {
        super.unbindIntents()
        disposable.clear()
    }
}

interface NotesView : MvpView {
    fun addNoteIntent(): Observable<Note>
    fun render(viewState: NotesViewState)
}

data class NotesViewState(val loading: Boolean, val notes: List<Note>) {
    companion object {
        fun showLoadingState() = NotesViewState(true, emptyList())
        fun showResultState(l: List<Note>) = NotesViewState(false, l)
    }
}