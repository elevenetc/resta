package com.elevenetc.android.resta.features.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.BaseMapFragment
import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.location.LocationNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_restaurants.*

class RestaurantsFragment : BaseMapFragment() {

    private val markers = mutableMapOf<String, Marker>()
    private val defaultZoom = 16f
    private val vm by lazy { appComponent.restaurants().viewModel() }

    init {
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        checkResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady() {
        checkLocationPermission()
    }

    private fun handleState(state: ScreenState) {

        if (state.currentLocation.loc != null) setCurrentLocation(state.currentLocation.loc)
        addMarkers(state.restaurants)

        if (state.error != null) {

            if (state.error is LocationNotAvailableException) {

                bottomSheet.showMessageWithAction(R.string.location_is_not_available, R.string.retry) {
                    loadRestaurants()
                }

            } else {
                bottomSheet.showMessageWithAction(R.string.error_getting_restaurants, R.string.retry) {
                    loadRestaurants()
                }
            }


        }
    }

    private fun loadRestaurants() {
        vm.getRests().subscribe({
            handleState(it)
        }, {

        })
    }

    private fun setCurrentLocation(currentLoc: Loc) {

        val latLng = LatLng(currentLoc.lat, currentLoc.lon)

        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(defaultZoom)
            .build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .anchor(.5f, .5f)
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location))
        )
    }

    private fun addMarkers(restaurants: Set<Restraunt>) {

        for (rest in restaurants) {

            if (markers.containsKey(rest.id)) continue

            val loc = LatLng(rest.loc.lat, rest.loc.lon)
            val marker = map.addMarker(
                MarkerOptions()
                    .title(rest.name)
                    .anchor(0.5f, 0.5f)
                    .position(loc)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_poi))
            )
            marker.tag = rest
            markers[rest.id] = marker
        }
    }

    private fun checkLocationPermission() {

        val permissions = appComponent.permissions()

        when {
            permissions.isLocationGranted() -> loadRestaurants()
            permissions.allowedToAksLocPermission() -> permissions.askLocPermissionDialog()
        }
    }

    private fun checkResume() {

        val permissions = appComponent.permissions()

        if (!permissions.isLocationGranted()) {
            bottomSheet.showMessageWithAction(R.string.location_access_is_not_granted, R.string.grant_location) {

                if (permissions.allowedToAksLocPermission()) {
                    permissions.askLocPermissionDialog()
                } else {
                    appComponent.permissions().askLocPermissionDialogOrAppSettings()
                }
            }
        } else {
            bottomSheet.hideMessage()
            loadRestaurants()
        }
    }
}