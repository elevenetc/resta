package com.elevenetc.android.resta.core

import android.app.Application
import com.elevenetc.android.resta.core.di.AppModule
import com.elevenetc.android.resta.core.di.DaggerAppComponent

class App : Application() {
    val appComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
}