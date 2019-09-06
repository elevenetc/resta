package com.elevenetc.android.resta.core.activity

import com.elevenetc.android.resta.features.restaurants.MainActivity

/**
 * Used to simplify Android SDK functions access.
 * Works only with single activity app.
 */
interface ActivityKeeper {
    fun keep(activity: MainActivity)
    fun onDestroy()
    fun get(): MainActivity
}