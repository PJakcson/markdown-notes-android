package com.notes.marcnmn.pandamarkdownnotes.navigation

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.notes.marcnmn.pandamarkdownnotes.model.Note
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.MainActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivity
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Created by marcneumann on 13.01.18.
 */

@Suppress("MemberVisibilityCanPrivate")
@Singleton
class Navigator @Inject constructor(val ctx: Context) {

    fun startHome() = ctx.startActivity(Intent(ctx, MainActivity::class.java))

    fun startWrite(n: Note) {
        val intent = Intent(ctx, WriteActivity::class.java)
        intent.putExtra("note", n as Parcelable)
        ctx.startActivity(intent)
    }
}