package com.elevenetc.android.resta.features.restaurants

import com.elevenetc.android.resta.features.restaurants.api.FoursquareModule
import dagger.Subcomponent

@Subcomponent(modules = [RestaurantsModule::class, FoursquareModule::class])
interface RestaurantsComponent {
    fun viewModel(): ViewModel
}