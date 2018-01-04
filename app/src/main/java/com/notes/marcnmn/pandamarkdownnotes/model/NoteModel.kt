package com.notes.marcnmn.pandamarkdownnotes.model

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.notes.marcnmn.pandamarkdownnotes.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton


/*
 * Created by marcneumann on 04.01.18.
 */

@Singleton
class NoteModel @Inject constructor(var storage: Storage) {
    val notes = MutableLiveData<MutableList<Note>>()

    fun findItemById(id: String) = notes.value?.find { it.id == id }

    fun updateItem(n: Note) {
        val f = notes.value ?: return
        val index = f.indexOfFirst { it.id == n.id }
        if (index < 0) {
            addItem(n)
            return
        }
        f[index] = n
        notes.postValue(f)

        Handler().post { flush() }
    }

    fun addItem(n: Note) {
        val v = mutableListOf(n)
        val f = notes.value
        if (f != null) v.addAll(0, f)
        notes.postValue(v)

        Handler().post { flush() }
    }

    fun removeItemById(id: String) {
        var f = notes.value ?: return
        f = f.filter { it.id != id }.toMutableList()
        notes.postValue(f)

        Handler().post { flush() }
    }

    fun flush() {
        storage.saveNotes(notes.value ?: listOf())
    }

    init {
        notes.postValue(storage.restoreNotes().toMutableList())
    }
}
