package com.elevenetc.android.resta.core.di

import android.content.Context
import com.elevenetc.android.resta.core.cache.RestsCache
import com.elevenetc.android.resta.core.cache.RestsCacheImpl
import com.elevenetc.android.resta.core.errors.ErrorMessenger
import com.elevenetc.android.resta.core.errors.ErrorMessengerImpl
import com.elevenetc.android.resta.core.location.LocManager
import com.elevenetc.android.resta.core.location.LocManagerImpl
import com.elevenetc.android.resta.core.logging.Logger
import com.elevenetc.android.resta.core.logging.LoggerImpl
import com.elevenetc.android.resta.core.permissions.PermissionsManager
import com.elevenetc.android.resta.core.permissions.PermissionsManagerImpl
import com.elevenetc.android.resta.core.scheduling.Schedulers
import com.elevenetc.android.resta.core.scheduling.SchedulersImpl
import com.elevenetc.android.resta.core.repository.RestsRepository
import com.elevenetc.android.resta.core.repository.RestsRepositoryImpl
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
    @Singleton
    fun locations(inst: LocManagerImpl): LocManager = inst

    @Provides
    fun errors(inst: ErrorMessengerImpl): ErrorMessenger = inst

    @Provides
    @Singleton
    fun restaurantsCache(inst: RestsCacheImpl): RestsCache = inst

    @Provides
    @Singleton
    fun restaurantsRepository(inst: RestsRepositoryImpl): RestsRepository = inst

    @Provides
    fun appContext(): Context {
        return appContext
    }
}