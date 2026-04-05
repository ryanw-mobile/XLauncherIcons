package com.rwmobi.xlaunchericons

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

class IconManager(private val context: Context) {
    private val packageManager: PackageManager = context.packageManager

    fun getActiveIconComponent(): String? = gregAppIcons.find {
        packageManager.getComponentEnabledSetting(
            ComponentName(context, it.component),
        ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    }?.component ?: gregAppIcons.firstOrNull()?.component

    fun setIcon(componentName: String) {
        gregAppIcons.filter {
            it.component != componentName
        }.forEach {
            packageManager.setComponentEnabledSetting(
                ComponentName(context, it.component),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP,
            )
        }

        packageManager.setComponentEnabledSetting(
            ComponentName(context, componentName),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP,
        )
    }
}
