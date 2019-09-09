package com.elevenetc.android.resta.features.map

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.usecases.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRestaurants @Inject constructor(
    private val restaurantsProvider: RestaurantsProvider
) : UseCase {
    fun get(bounds: MapBounds): Single<Set<Restaurant>> {
        return restaurantsProvider.get(bounds)
    }
}