package com.notes.marcnmn.pandamarkdownnotes.di

import com.notes.marcnmn.pandamarkdownnotes.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/*
 * Created by marcneumann on 31.12.17.
 */

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}