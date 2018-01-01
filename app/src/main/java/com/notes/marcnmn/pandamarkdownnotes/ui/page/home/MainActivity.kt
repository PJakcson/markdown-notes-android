package com.notes.marcnmn.pandamarkdownnotes.ui.page.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.notes.marcnmn.pandamarkdownnotes.R
import com.notes.marcnmn.pandamarkdownnotes.ui.BaseActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.page.write.WriteActivity

class MainActivity : BaseActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        android.os.Handler().postDelayed({
            startActivity(Intent(this, WriteActivity::class.java))
        }, 1000)
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
