package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.content.Context
import android.content.Intent
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivity
import javax.inject.Inject


/*
 * Created by marcneumann on 03.01.18.
 */
@Suppress("MemberVisibilityCanPrivate")
class NotesViewModel @Inject constructor(val ctx: Context, val model: NoteModel) : NotesAdapter.NoteSelected {
    val notes = model.notes

    fun addItem(n: Note) {
        model.addItem(n)
    }

    override fun selected(n: Note) {
        val intent = Intent(ctx, WriteActivity::class.java)
        intent.putExtra("id", n.id)
        ctx.startActivity(intent)
    }

    override fun removed(n: Note) {
        model.removeItemById(n.id)
    }
}

//@Suppress("UNCHECKED_CAST")
//class NotesViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
//    @Inject lateinit var ctx: Context
//    @Inject lateinit var nm: NoteModel
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) return NotesViewModel(ctx, nm) as T
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}