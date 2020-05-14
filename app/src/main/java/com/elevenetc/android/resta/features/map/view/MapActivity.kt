package com.elevenetc.android.resta.features.map.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elevenetc.android.resta.R

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_container, MapFragment())
                    .commit()
        }
    }
}
