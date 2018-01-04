package com.notes.marcnmn.pandamarkdownnotes.model

import java.io.Serializable
import java.util.*

/*
 * Created by marcneumann on 02.01.18.
 */

data class Note(val id: String, var raw: String, val created: Date, var edited: Date) : Serializable {
    constructor() : this(UUID.randomUUID().toString(), "", Calendar.getInstance().time, Calendar.getInstance().time)
}