package com.elevenetc.android.resta.features.map

import com.elevenetc.android.resta.core.location.MapBounds
import io.reactivex.Single

interface RestaurantsProvider {
    fun get(bounds: MapBounds): Single<Set<Restaurant>>
}