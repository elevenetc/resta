package com.elevenetc.android.resta.core.logging

interface Logger {
    fun log(throwable: Throwable)
    fun logD(message: String)
}