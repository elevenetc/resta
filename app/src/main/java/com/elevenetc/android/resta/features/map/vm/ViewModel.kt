package com.elevenetc.android.resta.features.map.vm

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.models.Restaurant


interface ViewModel {

    val state: LiveData<State>
    fun onAction(action: Action)

    sealed class Action {
        object VerifyGrantedLocationAccess : Action()
        data class RequestLocationAccess(val fragment: Fragment) : Action()
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
        data class CurrentLocationReceived(val loc: Loc) : State()
        object Loading : State()
        data class RestsCachedResult(val data: Set<Restaurant>) : State()
        data class RestsNetworkResult(val data: Set<Restaurant>) : State()
        data class RestsErrorResult(val error: Throwable) : State()
    }
}