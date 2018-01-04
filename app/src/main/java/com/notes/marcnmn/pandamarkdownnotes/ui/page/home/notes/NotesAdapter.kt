package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import java.util.*
import javax.inject.Inject

/*
 * Created by marcneumann on 03.01.18.
 */

class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private var selectedListener: NoteSelected? = null
    private var items = listOf<Note>()

    fun setNotes(n: List<Note>) {
        println("${items.hashCode()} ${n.hashCode()}")

        Collections.sort(n, { o1, o2 -> o2.edited.compareTo(o1.edited) })
        val diffRes = DiffUtil.calculateDiff(NotesDiffCallback(items, n))
        items = n
        diffRes.dispatchUpdatesTo(this)
    }

    fun setSelectedListener(s: NoteSelected) {
        selectedListener = s
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder?, p: Int) {
        if (h == null || p < 0 || p >= items.size) return
        h.updateModel(items[p])

        h.itemView.setOnClickListener {
            val pos = h.adapterPosition
            if (pos >= 0 && pos < items.size) selectedListener?.selected(items[pos])
        }

        h.itemView.setOnLongClickListener {
            val pos = h.adapterPosition
            if (pos >= 0 && pos < items.size) selectedListener?.removed(items[pos])
            true
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var title = v.findViewById<TextView>(R.id.title)
        private var content = v.findViewById<TextView>(R.id.content)
        private var edited = v.findViewById<TextView>(R.id.last_edited)

        fun updateModel(n: Note) {
            title.text = n.id
            content.text = n.raw
            edited.text = duration(Calendar.getInstance().time, n.edited)
        }
    }

    private fun duration(from: Date, to: Date): String {
        val dur = (from.time - to.time) / 1000
        return when {
            dur < 60 -> "${dur}s" // seconds
            dur < 3600 -> "${dur / 60 % 60}m" // minutes
            dur / 3600 < 24 -> "${dur / 3600}h" // hours
            dur / (3600 * 24) < 7 -> "${dur / (3600 * 24)}D" // days
            dur / (3600 * 24 * 7) < 5 -> "${dur / (3600 * 24 * 7)}W" // weeks
            dur / (3600 * 24 * 365) < 1 -> "${dur / (3600 * 24 * 31)}M" // months
            else -> "${dur / (3600 * 24 * 365)}Y" // years
        }
    }

    interface NoteSelected {
        fun selected(n: Note)
        fun removed(n: Note)
    }
}

class NotesDiffCallback(private val o: List<Note>, private val n: List<Note>) : DiffUtil.Callback() {

    override fun areItemsTheSame(op: Int, np: Int) = o[op].id == n[np].id

    override fun getOldListSize(): Int = o.size

    override fun getNewListSize(): Int = n.size

    override fun areContentsTheSame(op: Int, np: Int) = false
}