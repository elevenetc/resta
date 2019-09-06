package com.elevenetc.android.resta.features.restaurants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.activity().keep(this)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_container, RestaurantsFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        appComponent.activity().onDestroy()
        super.onDestroy()
    }
}
