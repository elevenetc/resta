package com.elevenetc.android.resta.features.map

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.scheduling.Schedulers
import com.elevenetc.android.resta.core.usecases.GetCurrentLocation
import io.reactivex.Single
import javax.inject.Inject

class ViewModelImpl @Inject constructor(
    private val schedulers: Schedulers,
    private val getCurrentLocationCase: GetCurrentLocation,
    private val getRestaurantsCase: GetRestaurants
) : ViewModel {

    override fun getCurrentLocation(): Single<Loc> {
        return getCurrentLocationCase.get()
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.ui())
    }

    override fun getRestaurants(bounds: MapBounds): Single<Set<Restaurant>> {
        return getRestaurantsCase.get(bounds)
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.ui())
    }
}