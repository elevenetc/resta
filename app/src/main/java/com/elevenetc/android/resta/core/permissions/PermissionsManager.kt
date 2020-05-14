package com.elevenetc.android.resta.core.permissions

import androidx.fragment.app.Fragment

interface PermissionsManager {
    fun requestLocationPermissionDialog(fragment: Fragment)
    fun requestPersmissionSettings(fragment: Fragment)
    fun isLocationGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean
    fun isLocationGranted(): Boolean
    fun allowedToAksLocPermission(fragment: Fragment): Boolean
}