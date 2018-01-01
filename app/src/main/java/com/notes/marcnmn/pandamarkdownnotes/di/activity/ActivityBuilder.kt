package com.notes.marcnmn.pandamarkdownnotes.di.activity

import com.notes.marcnmn.pandamarkdownnotes.di.ActivityScope
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.MainActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.MainActivityModule
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
}