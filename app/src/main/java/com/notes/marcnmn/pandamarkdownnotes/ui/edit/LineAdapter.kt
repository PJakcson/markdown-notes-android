package com.notes.marcnmn.pandamarkdownnotes.ui.edit

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.notes.marcnmn.pandamarkdownnotes.MarkdownEditText
import com.notes.marcnmn.pandamarkdownnotes.NewLine
import com.notes.marcnmn.pandamarkdownnotes.R

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.document_checkbox_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= list.size) return
        holder.textView?.addNewLineListener(nl)
        holder.textView?.setText(list[position])

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

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: MarkdownEditText? = null

        private var textWatcher: TextWatcher? = null
        private var checkBox: CheckBox? = null

        init {
            textView = itemView.findViewById(R.id.edit) as MarkdownEditText
            checkBox = itemView.findViewById(R.id.checkbox) as CheckBox
        }

        fun addListener(tw: TextWatcher) {
            if (textWatcher != null) return
            textView?.addTextChangedListener(tw)
        }

        fun removeListener() {
            if (textWatcher != null) textView?.removeTextChangedListener(textWatcher)
        }
    }
}