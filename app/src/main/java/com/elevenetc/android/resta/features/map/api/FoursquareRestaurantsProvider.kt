package com.elevenetc.android.resta.features.map.api

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.features.map.RestaurantsProvider
import com.elevenetc.android.resta.features.map.Restaurant
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named


class FoursquareRestaurantsProvider @Inject constructor(
    private val api: FoursquareApi,
    private val mapper: Mapper,
    private val logger: Logger,
    @Named(FoursquareModule.Names.VENUES_CATEGORY) private val category: String
) : RestaurantsProvider {
    override fun get(bounds: MapBounds): Single<Set<Restaurant>> {
        val sw = "${bounds.swLat},${bounds.swLon}"
        val ne = "${bounds.neLat},${bounds.neLon}"
        return api.get(
            sw,
            ne,
            "browse",
            category
        ).map {
            mapper.searchResponse(it)
        }.doOnError {
            logger.log(it)
        }
    }
}