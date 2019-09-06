package com.elevenetc.android.resta.core.activity

import com.elevenetc.android.resta.features.restaurants.MainActivity
import javax.inject.Inject

class ActivityKeeperImpl @Inject constructor(): ActivityKeeper {

    private var activity: MainActivity? = null

    override fun get(): MainActivity {
        return activity!!
    }

    override fun keep(activity: MainActivity) {
        this.activity = activity
    }

    override fun onDestroy() {
        this.activity = null
    }

}