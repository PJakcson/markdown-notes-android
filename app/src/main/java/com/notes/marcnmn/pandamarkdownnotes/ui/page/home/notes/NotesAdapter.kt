package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import javax.inject.Inject

/*
 * Created by marcneumann on 03.01.18.
 */

class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
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