package com.elevenetc.android.resta.features.map.api

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.features.map.Restaurant
import java.util.*
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun searchResponse(response: SearchResponse): Set<Restaurant> {
        val result = HashSet<Restaurant>()

        for (venue in response.response.venues) {
            result.add(
                Restaurant(
                    venue.id,
                    venue.name,
                    if (venue.categories.isNotEmpty()) venue.categories[0].name else "",
                    venue.location.address ?: "",
                    Loc(venue.location.lat, venue.location.lng)
                )
            )
        }
        return result
    }
}