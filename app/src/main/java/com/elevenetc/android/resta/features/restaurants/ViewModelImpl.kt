package com.elevenetc.android.resta.features.restaurants

import com.elevenetc.android.resta.core.location.CurrentLocation
import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.scheduling.Schedulers
import com.elevenetc.android.resta.core.usecases.GetCurrentLocation
import io.reactivex.Single
import javax.inject.Inject

class ViewModelImpl @Inject constructor(
    private val schedulers: Schedulers,
    private val getCurrentLocation: GetCurrentLocation,
    private val getRestaurants: GetRestaurants
) : ViewModel {

    private var currentState = ScreenState(CurrentLocation(), emptySet())

    override fun getRests(targetLoc: Loc?): Single<ScreenState> {

        return getCurrentLocation.get().flatMap { currentLoc ->
            getRestaurants.get(targetLoc ?: currentLoc).map {
                reduce(currentLoc, it)
            }.subscribeOn(schedulers.background()).observeOn(schedulers.ui())
        }.onErrorReturn {
            reduce(error = it)
        }.subscribeOn(schedulers.background()).observeOn(schedulers.ui())
    }

    private fun reduce(
        currentLocation: Loc? = null,
        restaurants: Set<Restraunt> = emptySet(),
        error: Throwable? = null
    ): ScreenState {

        val newCurrentLoc =
            if (currentState.currentLocation.loc == null) currentLocation else currentState.currentLocation.loc
        val newRests = currentState.restaurants + restaurants

        return ScreenState(
            CurrentLocation(newCurrentLoc),
            newRests,
            error
        )
    }
}