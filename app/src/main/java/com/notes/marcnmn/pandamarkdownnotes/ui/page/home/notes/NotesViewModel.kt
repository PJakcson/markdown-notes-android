package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import javax.inject.Inject


/*
 * Created by marcneumann on 03.01.18.
 */

class NotesViewModel : ViewModel() {
    val notes = MutableLiveData<List<Note>>()

    fun addItem(n: Note) {
        val v = mutableListOf(n)
        val f = notes.value
        if (f != null) v.addAll(0, f)
        notes.postValue(v)
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) return NotesViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}