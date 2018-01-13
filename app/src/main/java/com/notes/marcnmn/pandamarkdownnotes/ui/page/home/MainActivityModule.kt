package com.notes.marcnmn.pandamarkdownnotes.ui.page.home

import android.app.Activity
import dagger.Module
import dagger.Provides


/*
 * Created by marcneumann on 31.12.17.
 */

@Module
class MainActivityModule {

    @Provides
    fun provideContext(ac: MainActivity): Activity = ac
}