package com.sumitanantwar.gameoflife_kotlin_functional.model

enum class State {
    Alive,
    Dead,
    Unborn
}

data class Cell(val x: Int, val y: Int) {

    var state = State.Unborn
    var neighbours = listOf<Cell>()
}