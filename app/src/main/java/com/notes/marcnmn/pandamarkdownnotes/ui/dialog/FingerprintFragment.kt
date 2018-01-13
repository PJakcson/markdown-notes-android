package com.notes.marcnmn.pandamarkdownnotes.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.notes.marcnmn.pandamarkdownnotes.R


/*
 * Created by marcneumann on 13.01.18.
 */

class FingerprintFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val fl = FrameLayout(context!!)
        LayoutInflater.from(context!!).inflate(R.layout.view_fingerprint_dialog, fl)

        val builder = AlertDialog.Builder(activity!!)
        builder.setView(fl)
                .setIcon(R.drawable.ic_fingerprint)
                .setNegativeButton("Abbrechen", { _, _ -> })
        val d = builder.create()
        setupFingerprint(d)

        return d
    }

    private fun setupFingerprint(d: Dialog) {
        val fm = FingerprintManagerCompat.from(activity!!)

//        if (fm.isHardwareDetected)
//            Snackbar.make(view, "no hardware", Snackbar.LENGTH_LONG).show()
//
//        if (!fm.hasEnrolledFingerprints())
//            Snackbar.make(view, "no fingerprints", Snackbar.LENGTH_LONG).show()

        fm.authenticate(null, 0, null, object : FingerprintManagerCompat.AuthenticationCallback() {
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?)
                    = logAndDismiss("onAuthenticationError")

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?)
                    = logAndDismiss("onAuthenticationSucceeded")

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) = logAndDismiss("onAuthenticationHelp")

            override fun onAuthenticationFailed() = logAndDismiss("onAuthenticationFailed")

            private fun logAndDismiss(s: String) {
                println("Dialog result [$s]")
                d.dismiss()
            }

        }, null)
    }
}