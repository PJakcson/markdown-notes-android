package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.NoteModel
import javax.inject.Inject


/*
 * Created by marcneumann on 03.01.18.
 */

class NotesViewModel(private val model: NoteModel) : ViewModel() {
    val notes = model.notes

    fun addItem(n: Note) {
        model.addItem(n)
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Inject lateinit var nm: NoteModel

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) return NotesViewModel(nm) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}