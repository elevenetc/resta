package com.elevenetc.android.resta.features.restaurants

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.models.Restaurant


interface ViewModel {

    val state: LiveData<State>
    fun onAction(action: Action)

    sealed class Action {
        data class RequestCurrentLocation(val fragment: Fragment) : Action()
        object GetCurrentLocation : Action()
        data class GetRests(val bounds: MapBounds) : Action()
        data class PermissionGranted(
            val requestCode: Int,
            val permissions: Array<out String>,
            val grantResults: IntArray
        ) : Action()
    }

    sealed class State {
        object Idle : State()
        object NoGrantedLocationAccess : State()
        object LoadingLocation : State()
        data class ErrorLocationLoading(val error: Throwable) : State()
        data class CurrentLocation(val loc: Loc) : State()
        object Loading : State()
        data class CachedResult(val data: Set<Restaurant>) : State()
        data class NetworkResult(val data: Set<Restaurant>) : State()
        data class LoadingError(val error: Throwable) : State()
    }
}