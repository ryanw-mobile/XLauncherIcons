package com.rwmobi.xlaunchericons

import androidx.annotation.DrawableRes

data class AppIcon(
    val component: String,

    @DrawableRes
    val foregroundResource: Int,
)
