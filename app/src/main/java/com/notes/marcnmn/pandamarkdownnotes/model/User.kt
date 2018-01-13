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
data class User(
        val id: String,
        val created: Date,
        var edited: Date,
        val forename: String?,
        val surname: String?
) : Parcelable, Serializable {

    constructor() : this(
            UUID.randomUUID().toString(),
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            "", ""
    )
}