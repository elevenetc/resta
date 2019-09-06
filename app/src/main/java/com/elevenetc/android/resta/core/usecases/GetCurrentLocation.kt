package com.elevenetc.android.resta.core.usecases

import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.location.LocManager
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(
    private val locManager: LocManager
) : UseCase {
    fun get(): Single<Loc> {
        return locManager.getCurrentLocation()
    }
}