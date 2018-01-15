package com.notes.marcnmn.pandamarkdownnotes.model

import android.os.Handler
import com.notes.marcnmn.pandamarkdownnotes.storage.Storage
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton


/*
 * Created by marcneumann on 04.01.18.
 */

@Singleton
class NoteModel @Inject constructor(var storage: Storage) {
    var selectedId: BehaviorSubject<String> = BehaviorSubject.create()
    val notes: BehaviorSubject<List<Note>> = BehaviorSubject.createDefault(listOf())

    init {
        notes.onNext(storage.restoreNotes())
    }

    fun findItemById(id: String) = notes.value?.find { it.id == id }

    fun updateItem(n: Note) {
        val f = notes.value.toMutableList()
        val index = f.indexOfFirst { it.id == n.id }
        if (index < 0) {
            addItem(n)
            return
        }
        f[index] = n
        notes.onNext(f.toList())

        Handler().post { flush() }
    }

    fun select(id: String) = selectedId.onNext(id)

    fun addItem(n: Note) {
        val f = notes.value.toMutableList()
        f.add(n)
        notes.onNext(f.toList())
        Handler().post { flush() }
    }

    fun removeItemById(id: String) {
        notes.onNext(notes.value.filter { it.id != id })

        Handler().post { flush() }
    }

    private fun flush() {
        storage.saveNotes(notes.value ?: listOf())
    }
}
