package com.notes.marcnmn.pandamarkdownnotes.ui.page

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import com.notes.marcnmn.pandamarkdownnotes.ui.dialog.FingerprintFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Created by marcneumann on 01.01.18.
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isSecured()) checkFingerprint()
    }

    private fun checkFingerprint() {
        println("permission ${ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)}")

        val fm = FingerprintManagerCompat.from(this)
        println("hardware ${fm.isHardwareDetected}")
        println("fingerprints ${fm.hasEnrolledFingerprints()}")

        if (!fm.isHardwareDetected)
            Snackbar.make(container, "no hardware", Snackbar.LENGTH_LONG).show()

        if (!fm.hasEnrolledFingerprints())
            Snackbar.make(container, "no fingerprints", Snackbar.LENGTH_LONG).show()

        FingerprintFragment().show(supportFragmentManager, "dialog")
    }

    abstract protected fun isSecured(): Boolean
}