package com.notes.marcnmn.pandamarkdownnotes.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*


/*
 * Created by marcneumann on 02.01.18.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Note(
        val id: String,
        var owner: String,
        var text: String,
        val created: Date,
        var edited: Date,

        var secure: Boolean,

        var header: String,
        var description: String?,

        var readers: List<String>,
        var editors: List<String>
) : Serializable, Parcelable {
    constructor(owner: User, initialText: String?) : this(
            UUID.randomUUID().toString(),
            owner.id,
            initialText ?: "",
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            false,
            "",
            "",
            emptyList(),
            emptyList()
    )

    companion object {
        private val ls = System.lineSeparator()

        fun header(n: Note): String = n.text.split(ls).let { if (it.isEmpty()) "" else it[0] }
                .let { it.replace("#", "") }

        fun body(n: Note): String = n.text.split(ls).let { if (it.size < 2) "" else it[1] }
    }
}