package com.elevenetc.android.resta.core

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    protected val appComponent by lazy { (applicationContext as App).appComponent }
}