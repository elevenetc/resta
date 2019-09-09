package com.elevenetc.android.resta.features.map

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.location.MapBounds
import io.reactivex.Single


interface ViewModel {
    fun getCurrentLocation(): Single<Loc>
    fun getRestaurants(bounds: MapBounds): Single<Set<Restaurant>>
}