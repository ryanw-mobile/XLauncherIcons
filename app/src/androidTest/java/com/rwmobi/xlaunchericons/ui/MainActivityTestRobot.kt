/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 */

package com.rwmobi.xlaunchericons.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.rwmobi.xlaunchericons.R
import com.rwmobi.xlaunchericons.ui.test.XLauncherIconsTestRule

internal class MainActivityTestRobot(
    private val composeTestRule: XLauncherIconsTestRule,
) {
    // Actions
    fun printSemanticTree() {
        with(composeTestRule) {
            onRoot().printToLog("SemanticTree")
        }
    }

    fun clickOnIcon(componentName: String) {
        with(composeTestRule) {
            onNodeWithTag("AppIcon_$componentName").performClick()
        }
    }

    // Assertions
    fun assertSubscribeHeadingIsDisplayed() {
        with(composeTestRule) {
            onNodeWithText(text = activity.getString(R.string.subscribe_heading)).assertIsDisplayed()
        }
    }

    fun assertIconIsDisplayed(componentName: String) {
        with(composeTestRule) {
            onNodeWithTag("AppIcon_$componentName").assertIsDisplayed()
        }
    }
}
