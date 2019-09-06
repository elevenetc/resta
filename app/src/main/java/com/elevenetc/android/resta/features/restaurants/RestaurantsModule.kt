package com.elevenetc.android.resta.features.restaurants

import dagger.Module
import dagger.Provides

@Module
class RestaurantsModule {
    @Provides
    fun viewModel(inst:ViewModelImpl):ViewModel = inst
}