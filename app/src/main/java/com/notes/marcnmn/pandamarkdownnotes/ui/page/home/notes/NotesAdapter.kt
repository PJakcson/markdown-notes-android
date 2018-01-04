package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.R
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
        notifyDataSetChanged()
    }

    fun setSelectedListener(s: NoteSelected) {
        selectedListener = s
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_notes_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder?, p: Int) {
        if (h == null || p < 0 || p >= items.size) return
        h.updateModel(items[p])
        h.itemView.setOnClickListener {
            val pos = h.adapterPosition
            if (pos >= 0 && pos < items.size) selectedListener?.selected(items[pos])
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var title = v.findViewById<TextView>(R.id.title)
        private var content = v.findViewById<TextView>(R.id.content)
        private var edited = v.findViewById<TextView>(R.id.last_edited)

        fun updateModel(n: Note) {
            title.text = n.raw
            content.text = n.raw
            edited.text = "2W"
        }
    }

    interface NoteSelected {
        fun selected(n: Note)
    }
}