package com.notes.marcnmn.pandamarkdownnotes.ui.page.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.ui.page.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), TextWatcher {
    @Inject lateinit var app: Application
    @Inject lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("MainAc $ctx")

//        Handler().postDelayed({
//            startActivity(Intent(this, WriteActivity::class.java))
//        }, 4000)
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
