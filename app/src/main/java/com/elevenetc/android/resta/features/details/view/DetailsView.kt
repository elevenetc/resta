package com.elevenetc.android.resta.features.details.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.utils.OnBackKeyListener
import com.elevenetc.android.resta.core.utils.OnLayoutListener
import com.elevenetc.android.resta.core.models.Restaurant
import kotlinx.android.synthetic.main.view_details.view.*

class DetailsView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private enum class State { HIDDEN, TITLE, FULL_SCREEN }

    private var maxHeight = 0
    private var state = State.HIDDEN

    init {
        LayoutInflater.from(context).inflate(R.layout.view_details, this, true)
        setBackgroundResource(R.drawable.details_background)
        fullScreenBackground.alpha = 0f
        btnClose.visibility = View.GONE

        btnMore.setOnClickListener {
            showFullScreen()
        }

        btnClose.setOnClickListener {
            minimizeToTitle()
        }

        setOnClickListener {
            if (state == State.TITLE) {
                showFullScreen()
            }
        }

        viewTreeObserver.addOnGlobalLayoutListener(OnLayoutListener(this) {
            maxHeight = (parent as View).height
            translationY = maxHeight.toFloat()
        })

        setOnKeyListener(OnBackKeyListener({
            state == State.FULL_SCREEN
        }, {
            minimizeToTitle()
        }))
    }

    private fun minimizeToTitle() {

        state = State.TITLE
        isFocusableInTouchMode = false

        animate()
            .setInterpolator(FastOutSlowInInterpolator())
            .translationY(maxHeight - resources.getDimension(R.dimen.bottom_sheet_height)).start()
        fullScreenBackground.alpha = 0f
        btnMore.visibility = View.VISIBLE
        btnClose.visibility = View.GONE
    }

    private fun showFullScreen() {

        state = State.FULL_SCREEN
        isFocusableInTouchMode = true
        requestFocus()

        animate().translationY(0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .withEndAction {
                fullScreenBackground.alpha = 1f
            }
            .start()
        btnMore.visibility = View.GONE
        btnClose.visibility = View.VISIBLE
    }

    fun showTitle(rest: Restaurant) {

        state = State.TITLE
        isFocusableInTouchMode = false

        textName.text = rest.name
        textCategory.text =
            if (rest.category.isEmpty()) resources.getString(R.string.details_undefined) else rest.category
        textAddress.text =
            if (rest.address.isEmpty()) resources.getString(R.string.details_undefined) else rest.address

        animate()
            .setInterpolator(LinearOutSlowInInterpolator())
            .translationY(maxHeight - resources.getDimension(R.dimen.bottom_sheet_height)).start()
    }

    fun hide() {

        if (state == State.HIDDEN) return

        state = State.HIDDEN

        fullScreenBackground.alpha = 0f
        animate()
            .setInterpolator(FastOutSlowInInterpolator())
            .translationY(maxHeight.toFloat()).start()
    }
}