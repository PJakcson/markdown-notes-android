package com.notes.marcnmn.pandamarkdownnotes.model

import java.util.*

/*
 * Created by marcneumann on 02.01.18.
 */

data class Note(val id: String, val raw: String, val created: Date, val edited: Date) {
    constructor() : this(UUID.randomUUID().toString(), "", Calendar.getInstance().time, Calendar.getInstance().time)
}