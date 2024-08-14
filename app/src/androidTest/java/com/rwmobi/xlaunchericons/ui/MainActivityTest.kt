/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 */

package com.rwmobi.xlaunchericons.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rwmobi.xlaunchericons.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@kotlinx.coroutines.ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mainActivityTestRobot: MainActivityTestRobot

    @Before
    fun setUp() {
        mainActivityTestRobot = MainActivityTestRobot(composeTestRule)
    }

    @Test
    fun appNavigationLayoutTest() {
        // Quick test for Renovate to assert the App can start up properly
        with(mainActivityTestRobot) {
            assertSubscribeHeadingIsDisplayed()
        }
    }
}
