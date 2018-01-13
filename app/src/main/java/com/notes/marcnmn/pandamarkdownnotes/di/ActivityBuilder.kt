package com.notes.marcnmn.pandamarkdownnotes.di

import android.app.Activity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.MainActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


/*
 * Created by marcneumann on 31.12.17.
 */

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MM::class))
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(WM::class))
    abstract fun bindWriteActivity(): WriteActivity
}

@Module
abstract class ActivityModule<in T : Activity> {

    @Provides
    fun providesContext(f: T): Activity = f
}

@Module
class MM : ActivityModule<MainActivity>()

@Module
class WM : ActivityModule<MainActivity>()

