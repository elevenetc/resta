package com.elevenetc.android.resta.core.location

import io.reactivex.Single

interface LocManager {
    /**
     * Returns current location or [LocationNotAvailableException].
     * Permission should be checked before method is called.
     */
    fun getCurrentLocation(): Single<Loc>
}