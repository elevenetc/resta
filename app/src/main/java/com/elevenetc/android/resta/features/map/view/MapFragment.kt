package com.elevenetc.android.resta.features.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.BaseMapFragment
import com.elevenetc.android.resta.core.location.Loc
import com.elevenetc.android.resta.core.models.Restaurant
import com.elevenetc.android.resta.core.utils.MapUtils
import com.elevenetc.android.resta.features.map.vm.ViewModel
import com.elevenetc.android.resta.features.map.vm.ViewModel.Action
import com.elevenetc.android.resta.features.map.vm.ViewModel.State
import com.elevenetc.android.resta.features.map.vm.ViewModelImpl
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_map.*
import kotlin.collections.set


class MapFragment : BaseMapFragment() {

    private val markers = mutableMapOf<String, Marker>()
    private var currentLocationMarker: Marker? = null
    private var errorMessage: Snackbar? = null
    private val defaultZoom = 16f
    private lateinit var vm: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vmFactory = appComponent.map().viewModelFactory()
        vm = ViewModelProvider(this, vmFactory).get(ViewModelImpl::class.java)
    }

    override fun onResume() {
        super.onResume()
        vm.onAction(Action.VerifyGrantedLocationAccess)
    }

    override fun onMapReady() {

        map.setOnMarkerClickListener { m ->
            if (m != currentLocationMarker) openDetails(m.tag as Restaurant)
            true
        }

        map.setOnMapClickListener { detailsView.hide() }
        map.setOnCameraIdleListener { loadRestaurants() }

        vm.state.observe(viewLifecycleOwner, Observer { state -> handleState(state) })

        loadCurrentLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        vm.onAction(Action.PermissionGranted(requestCode, permissions, grantResults))
    }

    private fun loadCurrentLocation() {
        vm.onAction(Action.GetCurrentLocation)
    }

    private fun openDetails(rest: Restaurant) {
        detailsView.showTitle(rest)
    }

    private fun loadRestaurants() {
        vm.onAction(Action.GetRests(MapUtils.getBounds(map)))
    }

    private fun setCurrentLocation(loc: Loc) {

        val latLng = LatLng(loc.lat, loc.lon)
        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(defaultZoom).build()

        currentLocationMarker = MapUtils.addBitmapMarker(R.mipmap.map_icon_current_location, latLng, map)

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun addRestaurants(restaurants: Set<Restaurant>) {

        for (rest in restaurants) {

            if (markers.containsKey(rest.id)) continue

            val loc = LatLng(rest.loc.lat, rest.loc.lon)
            val marker = map.addMarker(
                    MarkerOptions()
                            .title(rest.name)
                            .anchor(0.5f, 0.5f)
                            .position(loc)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_restaurant))
            )
            marker.tag = rest
            markers[rest.id] = marker
        }
    }

    private fun showError(@StringRes title: Int, @StringRes action: Int, retry: () -> Unit) {
        detailsView.hide()
        errorMessage?.dismiss()
        errorMessage = appComponent.errors().show(title, action, retry, this)
    }

    private fun handleState(state: State?) {
        when (state) {
            is State.LoadingLocation -> {
                progressView.visibility = View.VISIBLE
            }
            is State.ErrorLocationLoading -> {
                showError(R.string.map_error_getting_current_location,
                        R.string.retry
                ) { loadCurrentLocation() }
            }
            is State.NoGrantedLocationAccess -> {
                progressView.visibility = View.GONE
                showError(R.string.location_access_is_not_granted, R.string.grant_location) {
                    vm.onAction(Action.RequestLocationAccess(this))
                }
            }
            is State.CurrentLocationReceived -> {
                setCurrentLocation(state.loc)
            }
            is State.Loading -> {
                errorMessage?.dismiss()
                progressView.visibility = View.VISIBLE
            }
            is State.RestsCachedResult -> {
                addRestaurants(state.data)
            }
            is State.RestsNetworkResult -> {
                progressView.visibility = View.GONE
                addRestaurants(state.data)
            }
            is State.RestsErrorResult -> {
                progressView.visibility = View.GONE
                showError(
                        R.string.map_error_getting_restaurants,
                        R.string.retry
                ) { loadRestaurants() }
            }
        }
    }
}
