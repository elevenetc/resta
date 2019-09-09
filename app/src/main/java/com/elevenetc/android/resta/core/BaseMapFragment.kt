package com.elevenetc.android.resta.core

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.fragment_map.*

open class BaseMapFragment : BaseFragment() {

    lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            this.map = map
            onMapReady()
        }
    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    open fun onMapReady() {

    }
}