package com.sumitanantwar.gameoflife_kotlin_functional.ui.activities.main

import com.sumitanantwar.gameoflife_kotlin_functional.mvp.MvpPresenter
import java.util.*



class MainPresenter(view: MainContract.View) : MvpPresenter<MainContract.View>(view), MainContract.Presenter {

    private lateinit var timer: Timer
    private var isPlaying = false

    //======= Presenter Lifecycle =======
    override fun onInit() {

    }

    override fun onDestroy() {

    }

    //======= Presenter Contract =======
    override fun play() {

        if (isPlaying) {
            isPlaying = false
            view?.shouldPause()
        }
        else {
            isPlaying = true
            view?.shouldPlay()
        }
    }

    override fun reset() {
        view?.reset(false)
    }

    override fun resetForGlider() {
        view?.reset(true)
    }
}