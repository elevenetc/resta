package com.elevenetc.android.resta.core.permissions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.elevenetc.android.resta.core.RequestCodes.LOCATION_PERMISSION
import com.elevenetc.android.resta.core.activity.ActivityKeeper
import javax.inject.Inject

class PermissionsManagerImpl @Inject constructor(
    private val context: Context,
    private val activityKeeper: ActivityKeeper
) : PermissionsManager {

    override fun isLocationGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        return isPermissionGranted(
            LOCATION_PERMISSION,
            Manifest.permission.ACCESS_FINE_LOCATION, requestCode, permissions, grantResults
        )
    }

    override fun isLocationGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun allowedToAksLocPermission(): Boolean {
        return activityKeeper.get().shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun askLocPermissionDialog() {
        activityKeeper.get().requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION)
    }

    override fun askLocPermissionDialogOrAppSettings() {
        if (allowedToAksLocPermission()) {
            askLocPermissionDialog()
        } else {
            val intent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", activityKeeper.get().packageName, null)
            }
            activityKeeper.get().startActivity(intent)
        }
    }

    private fun isPermissionGranted(
        checkCode: Int,
        checkPermission: String,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ): Boolean {
        if (requestCode == checkCode) {
            for ((index, perm) in permissions.withIndex()) {
                if (perm == checkPermission)
                    if (grantResults.size > index && grantResults[index] == PackageManager.PERMISSION_GRANTED)
                        return true
                break
            }
        }
        return false
    }
}