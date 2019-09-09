package com.elevenetc.android.resta.core.utils

import android.view.KeyEvent
import android.view.View

class OnBackKeyListener(val backCondition: () -> Boolean, val handleBack: () -> Unit) : View.OnKeyListener {
    override fun onKey(view: View, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            if (backCondition()) {
                handleBack()
                return true
            }
        }
        return false
    }
}