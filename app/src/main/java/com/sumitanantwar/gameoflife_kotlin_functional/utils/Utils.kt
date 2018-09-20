package com.sumitanantwar.gameoflife_kotlin_functional.utils

import android.widget.Button

fun Button.disable() {
    this.isEnabled = false
    this.alpha = 0.5F
}

fun Button.enable() {
    this.isEnabled = true
    this.alpha = 1.0F
}