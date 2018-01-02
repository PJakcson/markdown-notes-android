package com.notes.marcnmn.pandamarkdownnotes

import android.app.Activity
import android.app.Application
import android.app.Fragment
import com.notes.marcnmn.pandamarkdownnotes.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

/*
 * Created by marcneumann on 31.12.17.
 */

class App : Application(), HasActivityInjector, HasFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}