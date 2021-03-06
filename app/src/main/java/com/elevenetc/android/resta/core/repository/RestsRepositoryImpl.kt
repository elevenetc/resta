package com.elevenetc.android.resta.core.repository

import com.elevenetc.android.resta.core.cache.RestsCache
import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.features.map.api.RestsApi
import com.elevenetc.android.resta.core.repository.RestsRepository.Result
import io.reactivex.Observable
import io.reactivex.Observable.concat
import javax.inject.Inject

class RestsRepositoryImpl @Inject constructor(
        private val api: RestsApi,
        private val cache: RestsCache
) : RestsRepository {
    override fun getRestaurants(bounds: MapBounds): Observable<out Result> {
        return concat(getCached(bounds), loadAndCache(bounds))
    }

    private fun getCached(bounds: MapBounds) =
        cache.get(bounds)
            .map { Result.Cached(it) }
            .toObservable()

    private fun loadAndCache(bounds: MapBounds): Observable<out Result> =
        api.get(bounds)
            .flatMap { cache.store(it) }
            .map { Result.Network(it) }
            .cast(Result::class.java)
            .onErrorReturn { Result.NetworkError(it) }
            .toObservable()
}