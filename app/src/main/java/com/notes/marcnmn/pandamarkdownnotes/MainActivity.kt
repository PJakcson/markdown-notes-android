package com.notes.marcnmn.pandamarkdownnotes

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.edit.LineAdapter

class MainActivity : AppCompatActivity(), NewLine {
    private val adapter = LineAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun newLine() {
    }

    override fun navigateUp(cp: Int) {
        Snackbar.make(findViewById(android.R.id.content), "hoch! $cp", 1000).show()
    }

    override fun navigateDown(cp: Int) {
        Snackbar.make(findViewById(android.R.id.content), "runter! $cp", 1000).show()
    }
}
