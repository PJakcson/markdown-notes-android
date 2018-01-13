package com.notes.marcnmn.pandamarkdownnotes.storage

import android.app.Application
import android.content.Context
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.model.User
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject


/*
 * Created by marcneumann on 04.01.18.
 */

private val userFileName = "user"
private val notesFileName = "notes"

class Storage @Inject constructor(val app: Application) {
    fun saveNotes(l: List<Note>) = save(notesFileName, l)

    fun restoreNotes(): List<Note> {
        val l = restoreObject<List<*>>(notesFileName) ?: return listOf()
        val list = mutableListOf<Note>()
        l.forEach({ if (it is Note) list.add(it) })
        return list
    }

    fun saveUser(u: User) = save(userFileName, u)

    fun restoreUser(): User? = restoreObject(userFileName)

    private fun save(name: String, o: Any) {
        app.openFileOutput(name, Context.MODE_PRIVATE).use {
            val os = ObjectOutputStream(it)
            os.writeObject(o)
            os.close()
            it.close()
        }
    }

    inline private fun <reified T> restoreObject(name: String): T? {
        var obj: Any? = null
        try {
            app.openFileInput(name).use {
                val iis = ObjectInputStream(it)
                obj = iis.readObject()
                iis.close()
                it.close()
            }
        } catch (e: Exception) {
            return null
        }
        return if (obj is T) obj as T else null
    }
}