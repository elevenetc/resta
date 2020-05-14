package com.elevenetc.android.resta.features.map

import com.elevenetc.android.resta.features.map.vm.ViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [MapModule::class])
interface MapComponent {
    fun viewModelFactory(): ViewModelFactory
}