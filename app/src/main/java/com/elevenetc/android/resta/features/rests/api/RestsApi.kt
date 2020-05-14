package com.elevenetc.android.resta.features.rests.api

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.models.Restaurant
import io.reactivex.Single

interface RestsApi {
    fun get(bounds: MapBounds): Single<Set<Restaurant>>
}