package com.sumitanantwar.gameoflife_kotlin_functional.mvp

/**
 * Created by Sumit Anantwar on 3/13/18.
 */
interface MvpPresenterInterface<in V : MvpViewInterface> {
    fun onInit()
    fun destroy()
    fun onDestroy()
}