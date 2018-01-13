package com.notes.marcnmn.pandamarkdownnotes.di

import android.app.Activity
import android.support.v4.app.Fragment
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes.NotesFragment
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.editor.EditorFragment
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

    @ActivityScope
    @ContributesAndroidInjector(modules = [(EFM::class)])
    abstract fun bindEditorFragment(): EditorFragment

}

@Module
abstract class FragmentModule<in T : Fragment> {

    @Provides
    fun providesActivity(f: T): Activity = f.activity!!
}

@Module
class NFM : FragmentModule<NotesFragment>()

@Module
class EFM : FragmentModule<EditorFragment>()

