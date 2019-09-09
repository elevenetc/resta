package com.elevenetc.android.resta.features.map

import com.elevenetc.android.resta.features.map.api.FoursquareModule
import dagger.Subcomponent

@Subcomponent(modules = [RestaurantsModule::class, FoursquareModule::class])
interface RestaurantsComponent {
    fun viewModel(): ViewModel
}