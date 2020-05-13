package com.elevenetc.android.resta.features.restaurants.repository

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.models.Restaurant
import io.reactivex.Observable

interface RestsRepository {

    /**
     * First result of stream is always [Result.Cached]
     * Second one either [Result.Network] or [Result.NetworkError]
     */
    fun getRestaurants(bounds: MapBounds): Observable<out Result>

    sealed class Result {
        data class Cached(val data: Set<Restaurant>) : Result()
        data class Network(val data: Set<Restaurant>) : Result()
        data class NetworkError(val error: Throwable) : Result()
    }
}