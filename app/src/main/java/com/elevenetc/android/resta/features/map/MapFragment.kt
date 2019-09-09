package com.elevenetc.android.resta.features.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.BaseMapFragment
import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.utils.MapUtils
import com.elevenetc.android.resta.core.utils.OnFinishedCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BaseMapFragment() {

    private val restsMarkers = mutableMapOf<String, Marker>()
    private var currentLocation: Loc? = null
    private var currentLocationMarker: Marker? = null

    private var errorMessage: Snackbar? = null

    private val defaultZoom = 16f
    private val vm by lazy { appComponent.restaurants().viewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressView.visibility = View.GONE
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady() {
        checkLocationPermission()

        map.setOnCameraIdleListener {
            if (currentLocation != null) {
                loadRestaurants()
            }
        }

        map.setOnMarkerClickListener {
            if (it != currentLocationMarker) {
                openDetails(it.tag as Restaurant)
            }
            true
        }

        map.setOnMapClickListener {
            detailsView.setHidden()
        }
    }

    private fun openDetails(rest: Restaurant) {
        detailsView.showTitle(rest)
    }

    private fun loadCurrentLocation() {
        subs.add(vm.getCurrentLocation().subscribe({
            setCurrentLocation(it)
        }, {
            appComponent.errors().show(
                R.string.map_error_getting_current_location,
                R.string.retry,
                { loadCurrentLocation() },
                this
            )
        }))
    }

    private fun loadRestaurants() {

        progressView.visibility = View.VISIBLE

        subs.add(
            vm.getRestaurants(MapUtils.getBounds(map)).subscribe({
                errorMessage?.dismiss()
                progressView.visibility = View.GONE
                addRestaurants(it)
            }, {
                progressView.visibility = View.GONE
                errorMessage = appComponent.errors().show(
                    R.string.map_error_getting_restaurants,
                    R.string.retry,
                    { loadRestaurants() },
                    this
                )
            })
        )
    }

    private fun setCurrentLocation(currentLocation: Loc) {

        this.currentLocation = currentLocation
        val latLng = LatLng(currentLocation.lat, currentLocation.lon)
        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(defaultZoom).build()

        currentLocationMarker = MapUtils.addBitmapMarker(R.mipmap.map_icon_current_location, latLng, map)

        map.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition),
            OnFinishedCallback { loadRestaurants() }
        )
    }

    private fun addRestaurants(restaurants: Set<Restaurant>) {

        for (rest in restaurants) {

            if (restsMarkers.containsKey(rest.id)) continue

            val loc = LatLng(rest.loc.lat, rest.loc.lon)
            val marker = map.addMarker(
                MarkerOptions()
                    .title(rest.name)
                    .anchor(0.5f, 0.5f)
                    .position(loc)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_restaurant))
            )
            marker.tag = rest
            restsMarkers[rest.id] = marker
        }
    }

    private fun checkLocationPermission() {

        val permissions = appComponent.permissions()

        when {
            permissions.isLocationGranted() -> loadCurrentLocation()
            permissions.allowedToAksLocPermission(this) -> permissions.askLocPermissionDialog(this)
            else -> {
                showNoPermissionError()
            }
        }
    }

    private fun showNoPermissionError() {

        val permissions = appComponent.permissions()

        appComponent.errors().show(
            R.string.location_access_is_not_granted,
            R.string.grant_location,
            {
                if (permissions.allowedToAksLocPermission(this)) {
                    permissions.askLocPermissionDialog(this)
                } else {
                    appComponent.permissions().askLocPermissionDialogOrAppSettings(this)
                }
            },
            this
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (appComponent.permissions().isLocationGranted(requestCode, permissions, grantResults)) {
            loadCurrentLocation()
        } else {
            showNoPermissionError()
        }
    }
}