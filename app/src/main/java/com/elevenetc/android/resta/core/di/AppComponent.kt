package com.elevenetc.android.resta.core.di

import com.elevenetc.android.resta.core.errors.ErrorMessenger
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.core.permissions.PermissionsManager
import com.elevenetc.android.resta.core.scheduling.Schedulers
import com.elevenetc.android.resta.features.rests.RestsComponent
import com.elevenetc.android.resta.features.rests.api.foursquare.FoursquareModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FoursquareModule::class])
interface AppComponent {

    fun schedulers(): Schedulers
    fun logger(): Logger
    fun permissions(): PermissionsManager
    fun errors(): ErrorMessenger

    fun restaurants(): RestsComponent
}