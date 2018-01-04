package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_notes.*
import javax.inject.Inject


/*
 * Created by marcneumann on 02.01.18.
 */

class NotesFragment : Fragment(), NotesAdapter.NoteSelected {
    @Inject lateinit var adapter: NotesAdapter
    @Inject lateinit var factory: NotesViewModelFactory
    private lateinit var model: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this, factory).get(NotesViewModel::class.java)
        model.notes.observe(this, Observer { if (it != null) adapter.setNotes(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(activity)
        adapter.setSelectedListener(this)
        recycler.adapter = adapter

        add_button.setOnClickListener { model.addItem(Note()) }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun selected(n: Note) {
        view?.let { Snackbar.make(it, n.raw, Snackbar.LENGTH_SHORT).show() }
//        startActivity(Intent(activity, WriteActivity::class.java))
    }
}