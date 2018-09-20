package com.sumitanantwar.gameoflife_kotlin_functional.application

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Plant a Timber Debug Tree
        Timber.plant(Timber.DebugTree())
    }
}