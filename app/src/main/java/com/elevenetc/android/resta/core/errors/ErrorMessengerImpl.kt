package com.elevenetc.android.resta.core.errors

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elevenetc.android.resta.R
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ErrorMessengerImpl @Inject constructor() : ErrorMessenger {

    override fun show(messageText: Int, actionButtonText: Int, action: () -> Unit, fragment: Fragment): Snackbar {
        val sb = Snackbar.make(fragment.view!!, messageText, Snackbar.LENGTH_INDEFINITE)
        sb.view.setBackgroundColor(ContextCompat.getColor(fragment.context!!, R.color.colorPrimaryDark))
        sb.setAction(actionButtonText) { action() }.show()
        return sb
    }
}