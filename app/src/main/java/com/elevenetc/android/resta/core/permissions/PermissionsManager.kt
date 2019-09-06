package com.elevenetc.android.resta.core.permissions

import androidx.fragment.app.Fragment

interface PermissionsManager {
    fun askLocPermissionDialog()
    fun askLocPermissionDialogOrAppSettings()
    fun isLocationGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean
    fun isLocationGranted(): Boolean
    fun allowedToAksLocPermission(): Boolean
}