package com.elevenetc.android.resta.features.restaurants

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.usecases.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRestaurants @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository
) : UseCase {
    fun get(loc: Loc): Single<Set<Restraunt>> {
        return restaurantsRepository.get(loc, 100)
    }
}