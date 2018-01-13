package com.notes.marcnmn.pandamarkdownnotes.ui.page.home

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
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

        println("permission ${ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)}")

        val fm = FingerprintManagerCompat.from(this)
        println("hardware ${fm.isHardwareDetected}")
        println("fingerprints ${fm.hasEnrolledFingerprints()}")

        if (!fm.isHardwareDetected)
            Snackbar.make(container, "no hardware", Snackbar.LENGTH_LONG).show()

        if (!fm.hasEnrolledFingerprints())
            Snackbar.make(container, "no fingerprints", Snackbar.LENGTH_LONG).show()

        fm.authenticate(null, 0, null, object : FingerprintManagerCompat.AuthenticationCallback() {
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                super.onAuthenticationError(errMsgId, errString)
                println("onAuthenticationError")
            }

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                println("onAuthenticationSucceeded")
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpMsgId, helpString)
                println("onAuthenticationHelp")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                println("onAuthenticationFailed")
            }
        }, null)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.USE_FINGERPRINT), 0)
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
}
