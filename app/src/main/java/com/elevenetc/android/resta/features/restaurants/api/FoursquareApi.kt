package com.elevenetc.android.resta.features.restaurants.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {
    @GET("venues/search")
    fun get(@Query("radius") radius: Int,
            @Query("ll") location: String,
            @Query("intent") intent: String,
            @Query("categoryId") categoryId: String
    ): Single<SearchResponse>
}