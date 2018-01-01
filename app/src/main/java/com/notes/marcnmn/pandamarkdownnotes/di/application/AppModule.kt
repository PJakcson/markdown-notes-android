package com.notes.marcnmn.pandamarkdownnotes.di.application

import android.app.Application
import com.notes.marcnmn.pandamarkdownnotes.App
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
}