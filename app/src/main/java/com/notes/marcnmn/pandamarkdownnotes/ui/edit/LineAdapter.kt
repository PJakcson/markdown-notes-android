package com.notes.marcnmn.pandamarkdownnotes.ui.edit

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import com.notes.marcnmn.pandamarkdownnotes.MarkdownEditText
import com.notes.marcnmn.pandamarkdownnotes.NewLine

/*
 * Created by marcneumann on 28.12.17.
 */

class LineAdapter(private val nl: NewLine) : Adapter<LineAdapter.ViewHolder>() {
    private val list = mutableListOf<String>()

    fun addItem(s: String) {
        list.add(s)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MarkdownEditText(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= list.size) return
        val v = holder.itemView as MarkdownEditText
        v.addNewLineListener(nl)
        v.setText(list[position])

        holder.addListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pos = holder.adapterPosition
                if (s != null && !s.startsWith("\n\n") && pos >= 0) list[pos] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onViewRecycled(holder: ViewHolder?) {
        holder?.removeListener()
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: MarkdownEditText) : RecyclerView.ViewHolder(itemView) {
        var textWatcher: TextWatcher? = null

        fun addListener(tw: TextWatcher) {
            if (textWatcher != null) return
            (itemView as MarkdownEditText).addTextChangedListener(tw)
        }

        fun removeListener() {
            if (textWatcher != null) (itemView as MarkdownEditText).removeTextChangedListener(textWatcher)
        }
    }
}