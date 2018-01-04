package com.notes.marcnmn.pandamarkdownnotes.model

import android.arch.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Created by marcneumann on 04.01.18.
 */

@Singleton
class NoteModel @Inject constructor() {
    val notes = MutableLiveData<List<Note>>()

    fun addItem(n: Note) {
        val v = mutableListOf(n)
        val f = notes.value
        if (f != null) v.addAll(0, f)
        notes.postValue(v)
    }

    fun removeItemById(id: String) {
        var f = notes.value ?: return
        f = f.filter { it.id != id }
        notes.postValue(f)
    }
}
