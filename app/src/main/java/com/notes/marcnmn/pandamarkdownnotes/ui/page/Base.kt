package com.notes.marcnmn.pandamarkdownnotes.ui.page

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

/*
 * Created by marcneumann on 01.01.18.
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}

abstract class BaseFragment<V : MvpView, P : MviPresenter<V, *>> : MviFragment<V, P>() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}