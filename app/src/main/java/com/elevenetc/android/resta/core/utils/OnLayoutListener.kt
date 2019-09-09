package com.elevenetc.android.resta.core.utils

import android.view.View
import android.view.ViewTreeObserver

class OnLayoutListener(val view: View, val onLayout: () -> Unit) : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        onLayout()
    }
}