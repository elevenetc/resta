package com.elevenetc.android.resta.features.rests.api.foursquare

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.core.models.Restaurant
import com.elevenetc.android.resta.features.rests.api.RestsApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named


class FoursquareRestsApi @Inject constructor(
        private val api: FoursquareApi,
        private val mapper: Mapper,
        private val logger: Logger,
        @Named(FoursquareModule.Names.VENUES_CATEGORY) private val category: String
) : RestsApi {
    override fun get(bounds: MapBounds): Single<Set<Restaurant>> {
        val sw = "${bounds.swLat},${bounds.swLon}"
        val ne = "${bounds.neLat},${bounds.neLon}"
        return api.get(
                sw,
                ne,
                DEFAULT_INTENT,
                category
        ).map {
            mapper.searchResponse(it)
        }.doOnError {
            logger.log(it)
        }
    }

    companion object {
        private const val DEFAULT_INTENT = "browse"
    }
}