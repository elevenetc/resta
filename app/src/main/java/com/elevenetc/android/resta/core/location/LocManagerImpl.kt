package com.elevenetc.android.resta.core.location

import android.content.Context
import com.google.android.gms.location.*
import io.reactivex.Observer
import io.reactivex.Single
import javax.inject.Inject

class LocManagerImpl @Inject constructor(private val context: Context) : LocManager {

    @SuppressWarnings("MissingPermission")
    override fun getCurrentLocation(): Single<Loc> {

        val locProvider = LocationServices.getFusedLocationProviderClient(context)

        return Single.fromObservable { observer ->

            locProvider.lastLocation.addOnSuccessListener { loc ->

                if (loc == null) {
                    requestSingleUpdate(locProvider, observer)
                } else {
                    observer.onNext(Loc(loc.latitude, loc.longitude))
                    observer.onComplete()
                }
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun requestSingleUpdate(locProvider: FusedLocationProviderClient, observer: Observer<in Loc>) {
        val request = LocationRequest().apply {
            numUpdates = 1
            maxWaitTime = 1000
        }
        locProvider.requestLocationUpdates(request, object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                locProvider.removeLocationUpdates(this)

                val loc = result.lastLocation

                if (loc == null) {
                    completeWithError()
                } else {
                    observer.onNext(Loc(loc.latitude, loc.longitude))
                    observer.onComplete()
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) completeWithError()
            }

            private fun completeWithError() {
                observer.onError(LocationNotAvailableException())
                observer.onComplete()
            }
        }, null)
    }
}