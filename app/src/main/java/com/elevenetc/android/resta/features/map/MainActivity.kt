package com.elevenetc.android.resta.features.map

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.elevenetc.android.resta.R
import com.elevenetc.android.resta.core.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_container, MapFragment())
                .commit()
        }
    }
}
