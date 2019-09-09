package com.elevenetc.android.resta.core.utils

import androidx.annotation.DrawableRes
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.location.MapBounds
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

object MapUtils {
    fun getBounds(map: GoogleMap): MapBounds {
        val viewPort = map.projection.visibleRegion
        val sw = viewPort.latLngBounds.southwest
        val nw = viewPort.latLngBounds.northeast
        return MapBounds(sw.latitude, sw.longitude, nw.latitude, nw.longitude)
    }

    fun addBitmapMarker(@DrawableRes resourse: Int, loc: LatLng, map: GoogleMap): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(loc)
                .anchor(.5f, .5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_current_location))
        )
    }
}