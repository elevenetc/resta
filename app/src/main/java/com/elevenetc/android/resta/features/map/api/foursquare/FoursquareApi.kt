package com.elevenetc.android.resta.features.map.api.foursquare

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {
    @GET("venues/search")
    fun get(
        @Query("sw") sw: String,
        @Query("ne") ne: String,
        @Query("intent") intent: String,
        @Query("categoryId") categoryId: String
    ): Single<SearchResponse>
}