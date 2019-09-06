package com.elevenetc.android.resta.core.scheduling

import io.reactivex.Scheduler

interface Schedulers {
    fun background(): Scheduler
    fun ui(): Scheduler
}