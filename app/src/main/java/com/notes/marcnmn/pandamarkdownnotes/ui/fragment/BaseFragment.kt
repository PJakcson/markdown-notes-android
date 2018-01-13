package com.notes.marcnmn.pandamarkdownnotes.ui.fragment

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import dagger.android.support.AndroidSupportInjection

/*
 * Created by marcneumann on 13.01.18.
 */
abstract class BaseFragment<V : MvpView, P : MviPresenter<V, *>> : MviFragment<V, P>() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}