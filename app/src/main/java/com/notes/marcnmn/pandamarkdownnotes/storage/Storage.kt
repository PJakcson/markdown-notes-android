package com.notes.marcnmn.pandamarkdownnotes.storage

import android.app.Application
import android.content.Context
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject


/*
 * Created by marcneumann on 04.01.18.
 */


class Storage @Inject constructor(val app: Application) {
    fun saveNotes(l: List<Note>) {
        val fos = app.openFileOutput("notes", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(l)
        oos.close()
        fos.close()
    }

    fun restoreNotes(): List<Note> {
        try {
            val fis = app.applicationContext.openFileInput("notes")
            val iis = ObjectInputStream(fis)
            val obj = iis.readObject()
            iis.close()
            fis.close()

            if (obj !is List<*>) {
                return listOf()
            }

            val list = mutableListOf<Note>()
            obj.forEach({
                if (it is Note) list.add(it)
            })
            return list
        } catch (e: Exception) {
            return listOf()
        }
    }
}