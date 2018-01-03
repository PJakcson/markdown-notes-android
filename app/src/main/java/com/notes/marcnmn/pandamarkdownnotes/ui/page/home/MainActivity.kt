package com.notes.marcnmn.pandamarkdownnotes.ui.page.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.ui.BaseActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.home.notes.NotesFragment
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
//        when (item.itemId) {
//            R.id.navigation_home -> stub.addView(NotesFragment(this))
//            R.id.navigation_dashboard -> layoutInflater.inflate(R.layout.view_home_recent, stub, true)
//        }
        return true
    }
}
