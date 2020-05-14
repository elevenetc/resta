package com.elevenetc.android.resta.core.cache

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.models.Restaurant
import io.reactivex.Single

/**
 * Thread safe.
 */
interface RestsCache {
    fun store(restaurants: Set<Restaurant>): Single<Set<Restaurant>>
    fun get(bounds: MapBounds): Single<Set<Restaurant>>
}