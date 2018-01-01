package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.content.Context
import dagger.Module
import dagger.Provides


/*
 * Created by marcneumann on 31.12.17.
 */

@Module
class WriteActivityModule {

    @Provides
    fun provideContext(ac: WriteActivity): Context = ac
}