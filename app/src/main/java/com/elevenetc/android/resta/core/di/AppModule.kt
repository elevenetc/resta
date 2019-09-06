package com.elevenetc.android.resta.core.di

import android.content.Context
import com.elevenetc.android.resta.core.activity.ActivityKeeper
import com.elevenetc.android.resta.core.activity.ActivityKeeperImpl
import com.elevenetc.android.resta.core.location.LocManager
import com.elevenetc.android.resta.core.location.LocManagerImpl
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.core.logging.LoggerImpl
import com.elevenetc.android.resta.core.permissions.PermissionsManager
import com.elevenetc.android.resta.core.permissions.PermissionsManagerImpl
import com.elevenetc.android.resta.core.scheduling.Schedulers
import com.elevenetc.android.resta.core.scheduling.SchedulersImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Provides
    fun schedulers(inst: SchedulersImpl): Schedulers = inst

    @Provides
    fun logger(inst: LoggerImpl): Logger = inst

    @Provides
    fun permissions(inst: PermissionsManagerImpl): PermissionsManager = inst

    @Provides
    fun locations(inst: LocManagerImpl): LocManager = inst

    @Provides
    @Singleton
    fun activity(inst: ActivityKeeperImpl): ActivityKeeper = inst

    @Provides
    fun appContext(): Context {
        return appContext
    }
}