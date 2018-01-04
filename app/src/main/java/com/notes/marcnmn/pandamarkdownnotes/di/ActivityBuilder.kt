package com.notes.marcnmn.pandamarkdownnotes.di

import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.MainActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.MainActivityModule
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes.NotesFragment
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes.NotesFragmentModule
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/*
 * Created by marcneumann on 31.12.17.
 */

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(NotesFragmentModule::class))
    abstract fun bindNotesFragment(): NotesFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(WriteActivityModule::class))
    abstract fun bindWriteActivity(): WriteActivity
}