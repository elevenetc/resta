package com.elevenetc.android.resta.core.cache

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.models.Restaurant
import io.reactivex.Single
import javax.inject.Inject

/**
 * Simplified in-memory implementation.
 * Thread safe.
 */
class RestaurantsCacheImpl @Inject constructor() : RestaurantsCache {

    private val cache = mutableSetOf<Restaurant>()

    override fun store(restaurants: Set<Restaurant>): Single<Set<Restaurant>> {
        return Single.fromCallable {
            synchronized(cache) {
                cache.addAll(restaurants)
            }
            restaurants
        }
    }

    override fun get(bounds: MapBounds): Single<Set<Restaurant>> {

        return Single.fromCallable {
            val result = mutableSetOf<Restaurant>()

            synchronized(cache) {
                cache.forEach {
                    if (bounds.contains(it.loc)) result.add(it)
                }
            }

            result
        }

    }
}