package com.notes.marcnmn.pandamarkdownnotes.ui.page.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.ui.fragment.notes.NotesFragment
import com.notes.marcnmn.pandamarkdownnotes.ui.fragment.settings.SettingsFragment
import com.notes.marcnmn.pandamarkdownnotes.ui.page.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(this)
        if (supportFragmentManager.fragments.size <= 0) {
            supportFragmentManager.beginTransaction().replace(R.id.stub, NotesFragment()).commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val f: Fragment? = when (item.itemId) {
            R.id.navigation_notes -> NotesFragment()
            R.id.navigation_settings -> SettingsFragment()
            else -> null
        }
        f?.let { supportFragmentManager.beginTransaction().replace(R.id.stub, it).commit() }
        return true
    }

    override fun isSecured(): Boolean = false
}
