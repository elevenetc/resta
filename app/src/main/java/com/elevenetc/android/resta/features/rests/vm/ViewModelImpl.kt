package com.elevenetc.android.resta.features.rests.vm

import androidx.lifecycle.MutableLiveData
import com.elevenetc.android.resta.core.location.MapBounds
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.core.permissions.PermissionsManager
import com.elevenetc.android.resta.core.scheduling.Schedulers
import com.elevenetc.android.resta.core.usecases.GetCurrentLocation
import com.elevenetc.android.resta.core.usecases.GetRests
import com.elevenetc.android.resta.features.rests.vm.ViewModel.Action
import com.elevenetc.android.resta.features.rests.vm.ViewModel.State
import com.elevenetc.android.resta.features.rests.vm.ViewModel.State.Idle
import com.elevenetc.android.resta.core.repository.RestsRepository.Result.*
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ViewModelImpl @Inject constructor(
        private val schedulers: Schedulers,
        private val getCurrentLocationCase: GetCurrentLocation,
        private val getRestsCase: GetRests,
        private val permissions: PermissionsManager,
        private val logger: Logger
) : ViewModel, androidx.lifecycle.ViewModel() {

    override val state = MutableLiveData<State>(Idle)
    private val subs = CompositeDisposable()

    override fun onCleared() {
        subs.dispose()
    }

    override fun onAction(action: Action) {
        reduce(action, state.value!!)
    }

    private fun reduce(action: Action, currentState: State) {

        if (currentState == Idle && action == Action.GetCurrentLocation) {
            if (permissions.isLocationGranted()) {
                updateCurrentState(State.LoadingLocation)
                getCurrentLocation()
            } else {
                updateCurrentState(State.NoGrantedLocationAccess)
            }
        } else if (currentState == State.NoGrantedLocationAccess && action is Action.RequestLocationAccess) {
            if (permissions.allowedToAksLocPermission(action.fragment)) {
                permissions.requestLocationPermissionDialog(action.fragment)
            } else {
                permissions.requestPersmissionSettings(action.fragment)
            }
        } else if (currentState == State.NoGrantedLocationAccess && action is Action.PermissionGranted) {
            if (permissions.isLocationGranted(action.requestCode, action.permissions, action.grantResults)) {
                updateCurrentState(State.LoadingLocation)
                getCurrentLocation()
            } else {
                updateCurrentState(State.NoGrantedLocationAccess)
            }
        } else if (currentState is State.NoGrantedLocationAccess && action is Action.VerifyGrantedLocationAccess) {
            if (permissions.isLocationGranted()) {
                updateCurrentState(State.LoadingLocation)
                getCurrentLocation()
            } else {
                updateCurrentState(State.NoGrantedLocationAccess)
            }
        } else if (currentState is State.CurrentLocation && action is Action.GetRests) {
            getRestaurants(action.bounds)
        } else if (currentState is State.CachedResult && action is Action.GetRests) {
            getRestaurants(action.bounds)
        } else if (currentState is State.NetworkResult && action is Action.GetRests) {
            getRestaurants(action.bounds)
        } else if (currentState is State.LoadingError && action is Action.GetRests) {
            getRestaurants(action.bounds)
        } else {
            logger.logD("Unimplemented state. Current state: $currentState. Action: $action")
        }
    }

    private fun updateCurrentState(newState: State) {
        state.postValue(newState)
    }

    private fun getCurrentLocation() {
        subs.add(
                getCurrentLocationCase.get()
                        .subscribeOn(schedulers.background())
                        .observeOn(schedulers.ui())
                        .subscribe({ location ->
                            updateCurrentState(State.CurrentLocation(location))
                        }, { error ->
                            updateCurrentState(State.ErrorLocationLoading(error))
                        })
        )
    }

    private fun getRestaurants(bounds: MapBounds) {

        updateCurrentState(State.Loading)

        subs.add(
                getRestsCase.get(bounds)
                        .subscribeOn(schedulers.background())
                        .observeOn(schedulers.ui())
                        .subscribe({ result ->

                            when (result) {
                                is Cached -> {
                                    updateCurrentState(State.CachedResult(result.data))
                                }
                                is Network -> {
                                    updateCurrentState(State.NetworkResult(result.data))
                                }
                                is NetworkError -> {
                                    updateCurrentState(State.LoadingError(result.error))
                                }
                            }

                        }, {
                            updateCurrentState(State.LoadingError(it))
                        })
        )
    }
}