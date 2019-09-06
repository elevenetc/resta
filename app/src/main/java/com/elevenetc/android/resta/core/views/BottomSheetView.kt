package com.elevenetc.android.resta.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.elevenetc.android.resta.R
import kotlinx.android.synthetic.main.bottom_sheet.view.*

class BottomSheetView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var state = State.INVISIBLE

    init {
        LayoutInflater.from(context).inflate(R.layout.bottom_sheet, this)
        sheetMessage.translationY = resources.getDimension(R.dimen.bottom_sheet_double_height)
    }

    fun showMessageWithAction(@StringRes message: Int, @StringRes actionTitle: Int, action: () -> Unit) {

        textMessage.setText(message)
        btnAction.visibility = View.VISIBLE
        btnAction.setText(actionTitle)
        btnAction.setOnClickListener { action() }

        showMessageSheet()
    }

    fun showMessage(@StringRes message: Int) {
        textMessage.setText(message)
        btnAction.visibility = View.GONE
        showMessageSheet()
    }

    fun hideMessage() {
        sheetMessage.animate().translationY(resources.getDimension(R.dimen.bottom_sheet_double_height)).start()
        state = State.INVISIBLE
    }

    private fun showMessageSheet() {
        if (state == State.INVISIBLE) {
            sheetMessage.animate().translationY(resources.getDimension(R.dimen.bottom_sheet_height)).start()
            state = State.MESSAGE
        }
    }

    private enum class State {
        INVISIBLE, MESSAGE
    }
}