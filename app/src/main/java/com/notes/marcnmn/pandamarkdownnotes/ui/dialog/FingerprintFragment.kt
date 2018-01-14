package com.notes.marcnmn.pandamarkdownnotes.ui.dialog

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.notes.marcnmn.pandamarkdownnotes.R
import kotlinx.android.synthetic.main.view_fingerprint_dialog.*


/*
 * Created by marcneumann on 13.01.18.
 */

class FingerprintFragment : DialogFragment() {
    private val cs = CancellationSignal()
    private lateinit var fm: FingerprintManagerCompat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        fm = FingerprintManagerCompat.from(activity!!)
        fm.authenticate(null, 0, cs, object : FingerprintManagerCompat.AuthenticationCallback() {
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) = handleError(errMsgId, errString)
            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) = handleSuccess()
            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) = handleHelp(helpString)
            override fun onAuthenticationFailed() = handleFailed()
        }, null)
        return LayoutInflater.from(context!!).inflate(R.layout.view_fingerprint_dialog, container)
    }

    private fun handleError(id: Int, msg: CharSequence?) {
        println("Fingerprint error: $id - $msg")
    }

    private fun handleSuccess() {
        println("Fingerprint success")
        dismiss()
    }

    private fun handleHelp(msg: CharSequence?) {
        println("Fingerprint help: $msg")
        if (view == null || msg == null) return
        Snackbar.make(view!!, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun handleFailed() {
        println("Fingerprint failed")
        ObjectAnimator
                .ofFloat(icon, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
                .setDuration(1000)
                .start()
    }

    override fun onPause() {
        cs.cancel()
        super.onPause()
    }
}