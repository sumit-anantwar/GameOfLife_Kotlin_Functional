package com.sumitanantwar.gameoflife_kotlin_functional.ui.activities.main

import com.sumitanantwar.gameoflife_kotlin_functional.mvp.MvpPresenterInterface
import com.sumitanantwar.gameoflife_kotlin_functional.mvp.MvpViewInterface

object MainContract {

    interface View : MvpViewInterface {
        fun shouldPlay()
        fun shouldPause()

        fun reset(forGlider: Boolean)
    }

    interface Presenter : MvpPresenterInterface<View> {
        fun play()
        fun reset()
        fun resetForGlider()
    }
}