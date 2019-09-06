package com.elevenetc.android.resta.features.restaurants

import com.elevenetc.android.resta.core.location.Loc
import io.reactivex.Single

interface RestaurantsRepository {
    fun get(loc: Loc, distance: Int): Single<Set<Restraunt>>
}