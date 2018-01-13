package com.notes.marcnmn.pandamarkdownnotes.di

import android.app.Activity
import android.support.v4.app.Fragment
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes.NotesFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


/*
 * Created by marcneumann on 31.12.17.
 */


@Module
abstract class FragmentBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(NFM::class)])
    abstract fun bindNotesFragment(): NotesFragment
}

@Module
abstract class FragmentModule<in T : Fragment> {

    @Provides
    fun providesContext(f: T): Activity = f.activity!!
}

@Module
class NFM : FragmentModule<NotesFragment>()

