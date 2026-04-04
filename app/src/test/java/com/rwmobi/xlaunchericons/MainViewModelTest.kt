package com.rwmobi.xlaunchericons

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val iconManager = mockk<IconManager>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should refresh active icon`() = runTest {
        val expectedComponent = "com.rwmobi.xlaunchericons.MainActivityA"
        every { iconManager.getActiveIconComponent() } returns expectedComponent

        val viewModel = MainViewModel(iconManager)

        assertEquals(expectedComponent, viewModel.activeIconComponent.value)
    }

    @Test
    fun `setIcon should call iconManager and update state`() = runTest {
        val newComponent = "com.rwmobi.xlaunchericons.MainActivityB"
        val viewModel = MainViewModel(iconManager)

        viewModel.setIcon(newComponent)
        testDispatcher.scheduler.advanceUntilIdle()

        verify { iconManager.setIcon(newComponent) }
        assertEquals(newComponent, viewModel.activeIconComponent.value)
    }
}
