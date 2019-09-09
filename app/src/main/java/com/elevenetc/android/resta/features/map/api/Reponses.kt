package com.elevenetc.android.resta.features.map.api

data class SearchResponse(val response: Response)
data class Response(val venues: List<FSVenue>)
data class FSVenue(val id: String, val name: String, val location: FSLoc, val categories:List<FSCategory>)
data class FSCategory(val name: String)

data class FSLoc(
    val lat: Double, val lng: Double,
    val distance: Int,
    val country: String,
    val address: String?
)