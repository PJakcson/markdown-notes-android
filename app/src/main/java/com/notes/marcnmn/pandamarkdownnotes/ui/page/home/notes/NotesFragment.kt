package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.view_notes_content.*
import javax.inject.Inject

/*
 * Created by marcneumann on 02.01.18.
 */

class NotesFragment : Fragment(), Adapter.NoteSelected {
    @Inject lateinit var adapter: Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.view_notes_content, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(activity)
        adapter.setSelectedListener(this)
        recycler.adapter = adapter
        adapter.setNotes(listOf(Note("a"), Note("b"), Note("c"), Note("d")))
    }

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun selected(n: Note) {
        Snackbar.make(view, n.raw, Snackbar.LENGTH_SHORT).show()
        startActivity(Intent(activity, WriteActivity::class.java))
    }
}

class Adapter @Inject constructor() : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var selectedListener: NoteSelected? = null
    private val items = mutableListOf<Note>()

    fun setNotes(n: List<Note>) {
        items.clear()
        items.addAll(n)
    }

    fun setSelectedListener(s: NoteSelected) {
        selectedListener = s
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent?.context))
    }

    override fun onBindViewHolder(h: ViewHolder?, p: Int) {
        if (h == null || p < 0 || p >= items.size) return
        h.text.text = items[p].raw
        h.text.setOnClickListener {
            val pos = h.adapterPosition
            if (pos >= 0 && pos < items.size) selectedListener?.selected(items[pos])
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val text: TextView) : RecyclerView.ViewHolder(text)

    interface NoteSelected {
        fun selected(n: Note)
    }
}