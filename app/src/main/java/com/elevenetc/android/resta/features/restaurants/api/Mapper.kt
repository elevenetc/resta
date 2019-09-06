package com.elevenetc.android.resta.features.restaurants.api

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.features.restaurants.Restraunt
import java.util.*
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun searchResponse(response: SearchResponse): Set<Restraunt> {
        val result = HashSet<Restraunt>()

        for (venue in response.response.venues) {
            result.add(
                Restraunt(
                    venue.id,
                    venue.name,
                    Loc(venue.location.lat, venue.location.lng)
                )
            )
        }
        return result
    }
}