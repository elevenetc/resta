package com.elevenetc.android.resta.features.restaurants

import com.elevenetc.android.resta.core.location.Loc
import io.reactivex.Single


interface ViewModel {
    fun getRests(targetLoc: Loc? = null): Single<ScreenState>
}