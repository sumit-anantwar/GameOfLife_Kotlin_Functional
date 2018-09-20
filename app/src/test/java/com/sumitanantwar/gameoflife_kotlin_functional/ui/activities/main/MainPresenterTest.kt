package com.sumitanantwar.gameoflife_kotlin_functional.ui.activities.main

import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify

import org.hamcrest.CoreMatchers.`is` as _is

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentCaptor

class MainPresenterTest {

    // Mocks
    private lateinit var mockView: MainContract.View

    // System Under Test
    private lateinit var mainPresenter_SUT: MainPresenter

    @Before
    fun setUp() {
        mockView = mock {}

    }

    @After
    fun tearDown() {
    }

    @Test
    fun tap_PlayButton_Once() {
        // GIVEN
        mainPresenter_SUT = MainPresenter(mockView)

        // WHEN Play button tapped once
        mainPresenter_SUT.play()

        // THEN
        // shouldPlay() is called once during first tap
        verify(mockView, times(1)).shouldPlay()
        verify(mockView, times(0)).shouldPause()
    }

    @Test
    fun tap_PlayButton_Twice() {
        // GIVEN
        mainPresenter_SUT = MainPresenter(mockView)

        // WHEN Play button tapped once
        mainPresenter_SUT.play()
        mainPresenter_SUT.play()

        // THEN
        // shouldPlay() is called once during first tap
        // shouldPause() is called once during second tap
        verify(mockView, times(1)).shouldPlay()
        verify(mockView, times(1)).shouldPause()
    }

    @Test
    fun tap_ResetButton() {
        val ac = ArgumentCaptor.forClass(Boolean::class.java)

        // GIVEN
        mainPresenter_SUT = MainPresenter(mockView)

        // WHEN
        mainPresenter_SUT.reset()

        // THEN
        verify(mockView, times(1)).reset(ac.capture())

        // Assert
        val captors = ac.allValues
        assertThat(captors[0], _is(false))
    }

    @Test
    fun tap_GliderButton() {
        val ac = ArgumentCaptor.forClass(Boolean::class.java)

        // GIVEN
        mainPresenter_SUT = MainPresenter(mockView)

        // WHEN
        mainPresenter_SUT.resetForGlider()

        // THEN
        verify(mockView, times(1)).reset(ac.capture())

        // Assert
        val captors = ac.allValues
        assertThat(captors[0], _is(true))
    }

}