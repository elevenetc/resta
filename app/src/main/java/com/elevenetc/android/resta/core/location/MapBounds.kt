package com.elevenetc.android.resta.core.location

data class MapBounds(
    val swLat: Double, val swLon: Double,
    val neLat: Double, val neLon: Double
) {
    fun contains(loc: Loc): Boolean {

        if (neLat >= loc.lat && loc.lat >= swLat)
            if (swLon <= neLon && swLon <= loc.lon && loc.lon <= neLon)
                return true
            else if (swLon > neLon && (swLon <= loc.lon || loc.lon <= neLon))
                return true

        return false
    }
}
