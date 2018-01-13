package com.notes.marcnmn.pandamarkdownnotes.di

import android.app.Application
import android.content.Context
import com.notes.marcnmn.pandamarkdownnotes.App
import com.notes.marcnmn.pandamarkdownnotes.model.User
import com.notes.marcnmn.pandamarkdownnotes.storage.Storage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/*
 * Created by marcneumann on 31.12.17.
 */

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: App): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app

    @Provides
    @Singleton
    fun provideUser(storage: Storage): User = storage.restoreUser() ?: User()
}