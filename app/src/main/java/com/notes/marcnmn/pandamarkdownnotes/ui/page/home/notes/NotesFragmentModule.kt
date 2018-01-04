package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes

import android.content.Context
import dagger.Module
import dagger.Provides


/*
 * Created by marcneumann on 31.12.17.
 */

@Module
class NotesFragmentModule {

    @Provides
    fun provideContext(ac: NotesFragment): Context = ac.activity!!
}