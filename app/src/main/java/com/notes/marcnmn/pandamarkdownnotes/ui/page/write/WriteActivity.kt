package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.ui.BaseActivity
import javax.inject.Inject

class WriteActivity : BaseActivity(), TextWatcher {
    @Inject lateinit var app: Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
