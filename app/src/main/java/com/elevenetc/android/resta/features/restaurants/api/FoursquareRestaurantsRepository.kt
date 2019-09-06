package com.elevenetc.android.resta.features.restaurants.api

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.features.restaurants.Restraunt
import com.elevenetc.android.resta.features.restaurants.RestaurantsRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named


class FoursquareRestaurantsRepository @Inject constructor(
    private val api: FoursquareApi,
    private val mapper: Mapper,
    private val logger: Logger,
    @Named(FoursquareModule.Names.VENUES_CATEGORY) private val category: String
) : RestaurantsRepository {
    override fun get(loc: Loc, distance: Int): Single<Set<Restraunt>> {
        val locString = "${loc.lat},${loc.lon}"
        return api.get(distance,
                locString,
                "browse",
                category
        ).map {
            mapper.searchResponse(it)
        }.doOnError {
            logger.log(it)
        }
    }
}