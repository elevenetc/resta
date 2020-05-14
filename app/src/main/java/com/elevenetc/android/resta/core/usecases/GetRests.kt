package com.elevenetc.android.resta.core.usecases

import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.repository.RestsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetRests @Inject constructor(
    private val repository: RestsRepository
) : UseCase {
    fun get(bounds: MapBounds): Observable<out RestsRepository.Result> {
        return repository.getRestaurants(bounds)
    }
}