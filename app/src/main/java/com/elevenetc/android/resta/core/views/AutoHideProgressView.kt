package com.elevenetc.android.resta.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

/**
 * Allows to call [showUnless] multiple times without flickering/stopping progress animation
 */
class AutoHideProgressView : ProgressBar {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val hideDelay = 1000L
    private var retryCallback: (() -> Unit)? = null

    /**
     * @param shouldBeGone defines condition if progress should be hidden
     */
    fun showUnless(shouldBeGone: () -> Boolean) {
        if (visibility == View.VISIBLE) return
        visibility = View.VISIBLE
        internalShow(shouldBeGone)
    }

    fun hide() {
        visibility = View.GONE
        removeCallbacks(retryCallback)
    }

    private fun internalShow(shouldBeGone: () -> Boolean) {
        retryCallback = {
            if (isAttachedToWindow)
                if (shouldBeGone()) visibility = View.GONE
                else internalShow(shouldBeGone)
        }
        postDelayed(retryCallback, hideDelay)
    }
}