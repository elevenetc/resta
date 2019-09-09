package com.elevenetc.android.resta.core.utils

import com.google.android.gms.maps.GoogleMap

class OnFinishedCallback(val onFinished: () -> Unit) : GoogleMap.CancelableCallback {
    override fun onFinish() {
        onFinished()
    }

    override fun onCancel() {

    }
}