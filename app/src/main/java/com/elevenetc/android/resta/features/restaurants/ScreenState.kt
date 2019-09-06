package com.elevenetc.android.resta.features.restaurants

import com.elevenetc.android.resta.core.location.CurrentLocation

data class ScreenState(
    val currentLocation: CurrentLocation,
    val restaurants: Set<Restraunt>,
    val error: Throwable? = null
)