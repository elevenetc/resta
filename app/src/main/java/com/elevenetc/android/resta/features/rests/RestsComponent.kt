package com.elevenetc.android.resta.features.rests

import dagger.Subcomponent

@Subcomponent(modules = [RestsModule::class])
interface RestsComponent {
    fun viewModelFactory(): RestsModule.ViewModelFactory
}