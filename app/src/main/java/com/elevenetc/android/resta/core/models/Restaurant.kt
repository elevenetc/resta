package com.elevenetc.android.resta.core.models

import com.elevenetc.android.resta.core.location.Loc

data class Restaurant(
        val id: String,
        val name: String,
        val category: String,
        val address: String,
        val loc: Loc
)