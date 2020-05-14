package com.elevenetc.android.resta.core.errors

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

interface ErrorMessenger {
    fun show(
        @StringRes messageText: Int,
        @StringRes actionButtonText: Int,
        action: () -> Unit,
        fragment: Fragment
    ): Snackbar

    fun show(messageText: Int, actionButtonText: Int, action: () -> Unit, view: View): Snackbar
}