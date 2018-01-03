package com.notes.marcnmn.pandamarkdownnotes.ui.page.home.settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.notes.marcnmn.pandamarkdownnotes.R

/*
 * Created by marcneumann on 03.01.18.
 */

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_settings, container, false)
    }
}