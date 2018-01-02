package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import dagger.android.AndroidInjection
import javax.inject.Inject

/*
 * Created by marcneumann on 02.01.18.
 */

class NotesFragment : android.app.Fragment() {
    @Inject lateinit var adapter: Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.view_notes_content, container, false)
        rootView.findViewById<RecyclerView>(R.id.recycler).layoutManager = LinearLayoutManager(rootView.context)
        rootView.findViewById<RecyclerView>(R.id.recycler).adapter = adapter
        adapter.setNotes(listOf(Note("yoyo"), Note("yoyo"), Note("yoyo"), Note("yoyo")))

        return rootView
    }

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }
}

class Adapter @Inject constructor() : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val items = mutableListOf<Note>()

    fun setNotes(n: List<Note>) {
        items.clear()
        items.addAll(n)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val tv = TextView(parent?.context)
        tv.text = "hallo"
        return ViewHolder(tv)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {}

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}