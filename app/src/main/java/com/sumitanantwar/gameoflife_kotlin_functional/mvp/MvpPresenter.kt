package com.sumitanantwar.gameoflife_kotlin_functional.mvp

import timber.log.Timber

/**
 * Created by Sumit Anantwar on 3/14/18.
 */
abstract class MvpPresenter<V: MvpViewInterface>(var view: V?) : MvpPresenterInterface<V> {

    init {
        this.onInit()
    }

    override fun destroy() {
        // Release the view
        view = null
        // Delegate onDestroy()
        onDestroy()
    }
}