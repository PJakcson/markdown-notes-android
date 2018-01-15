package com.notes.marcnmn.pandamarkdownnotes.ui.fragment.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.markdown.MarkdownFormatter
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import com.notes.marcnmn.pandamarkdownnotes.model.User
import com.notes.marcnmn.pandamarkdownnotes.ui.fragment.BaseFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_editor.*
import java.util.*
import javax.inject.Inject

/*
 * Created by marcneumann on 10.01.18.
 */

private val argNote = "note"

class EditorFragment : BaseFragment<EditorView, EditorPresenter>(), EditorView {
    @Inject lateinit var formatter: MarkdownFormatter
    @Inject lateinit var presenter: EditorPresenter

    private var toggleButton: ImageButton? = null
    private var secureSbj = PublishSubject.create<Any>()

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
        toggleButton = activity?.findViewById(R.id.btn_toggle_lock)
        super.onViewCreated(view, savedInstanceState)
        edit.formatter = formatter

        toggleButton?.let { RxView.clicks(it).subscribe(secureSbj::onNext) }
    }

    override fun afterTextChanged(): Observable<Editable?> = RxTextView.afterTextChangeEvents(edit).map { it.editable() }

    @SuppressLint("CheckResult")
    override fun toggleSecureState() = secureSbj.hide()

    override fun render(viewState: EditorViewState) {
        val rid = if (viewState.secure) R.drawable.ic_lock_outline_white else R.drawable.ic_lock_open_white
        toggleButton?.setImageResource(rid)

        if (viewState.initial) {
            edit.setText(viewState.text, TextView.BufferType.EDITABLE)
        } else {
            val sid = if (viewState.secure) R.string.editor_note_secured else R.string.editor_note_unsecured
            Snackbar.make(view!!, sid, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.editor_note_security_undo, secureSbj::onNext)
                    .show()
        }
    }

    override fun createPresenter() = presenter.withNote(arguments?.getParcelable(argNote))

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView(retainInstance)
    }
}


class EditorPresenter @Inject constructor(val model: NoteModel, val user: User) : MviBasePresenter<EditorView, EditorViewState>() {
    lateinit var note: Note
    private val disposable = CompositeDisposable()

    fun withNote(n: Note?): EditorPresenter {
        note = n ?: Note(user, "")
        return this
    }

    override fun bindIntents() {
        val obs = intent { it.toggleSecureState() }.map {
            note.secure = !note.secure
            note
        }.doOnNext(model::updateItem)
                .map { EditorViewState(it.text, it.secure, false) }
                .startWith(EditorViewState(note.text, note.secure, true))

        intent { it.afterTextChanged() }.map {
            note.text = it.toString()
            note.edited = Calendar.getInstance().time
            note
        }.subscribe(model::updateItem).addTo(disposable)


        subscribeViewState(obs, EditorView::render)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        disposable.clear()
    }
}

interface EditorView : MvpView {
    fun toggleSecureState(): Observable<Any>
    fun afterTextChanged(): Observable<Editable?>
    fun render(viewState: EditorViewState)
}

data class EditorViewState(val text: String, val secure: Boolean, val initial: Boolean)