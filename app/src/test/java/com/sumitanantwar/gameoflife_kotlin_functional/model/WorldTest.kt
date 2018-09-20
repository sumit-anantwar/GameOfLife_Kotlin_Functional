package com.sumitanantwar.gameoflife_kotlin_functional.model

import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as _is
import org.junit.Test

import org.junit.Before
import kotlin.system.measureTimeMillis


class WorldTest {

    val GRID_WIDTH = 10
    val GRID_HEIGHT = 10

    val world = World(GRID_WIDTH, GRID_HEIGHT)

    @Before
    fun setUp() {
        world.initialize()
    }


    //======= Test Cells =======

    @Test
    fun fetch_cellAtCoordinateZero_returnsValidCell() {

        // Test Cell @ (0, 0)
        val expectedX = 0; val expectedY = 0

        val cell = world[expectedX, expectedY]

        // Assert that the cell is not Null
        assertThat(cell, notNullValue())

        cell ?: return // Unwrap

        // Assert that the position coordinate are same as expected
        assertThat(cell.x, _is(expectedX))
        assertThat(cell.y, _is(expectedY))
    }

    @Test
    fun fetch_cellAtCoordinateWithinGridBounds_returnsValidCell() {

        // Test Cell @ (x < gridWidth, y < gridHeight)
        val expectedX = 5;
        val expectedY = 7

        val cell = world[expectedX, expectedY]

        // Assert that the cell is not Null
        assertThat(cell, notNullValue())

        cell ?: return // Unwrap

        // Assert that the position coordinate are same as expected
        assertThat(cell.x, _is(expectedX))
        assertThat(cell.y, _is(expectedY))

    }

    @Test
    fun fetch_cellAtCoordinateOverGridBounds_returnsNullCell() {

        // ==========================================
        // Test Cell @ (x = gridWidth, y = gridHeight)
        var expectedX = GRID_WIDTH
        var expectedY = GRID_HEIGHT

        val cell0 = world[expectedX, expectedY]

        // Assert that the cell is not Null
        assertThat(cell0, nullValue())

        // ==========================================
        // Test Cell @ (x > gridWidth, y > gridHeight)
        expectedX = GRID_WIDTH + 2
        expectedY = GRID_HEIGHT + 2

        val cell1 = world[expectedX, expectedY]

        // Assert that the cell is not Null
        assertThat(cell1, nullValue())
    }

    @Test
    fun fetch_cellAtCoordinateBelowZero_returnsNullCell() {

        // Test Cell @ (x >= gridWidth, y >= gridHeight)
        val expectedX = -1; val expectedY = -1

        val cell = world[expectedX, expectedY]

        // Assert that the cell is not Null
        assertThat(cell, nullValue())
    }


    //======= Test Neighbours =======
    @Test
    fun count_cellAtZero_hasThreeNeighbours() {
        // Fetch cell @ (0, 0)
        val cell = world[0, 0]

        cell ?: return // Unwrap

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        // Cell @ (0, 0) has 3 neighbours (1, 0) (1, 1) and (0, 1)
        assertThat(neighbourCount, _is(3))
    }

    @Test
    fun count_cellAtTopRightCorner_hasThreeNeighbours() {

        val cell = world[GRID_WIDTH - 1, 0]

        cell ?: return // Unwrap

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        assertThat(neighbourCount, _is(3))
    }

    @Test
    fun count_cellAtBottomRightCorner_hasThreeNeighbours() {

        val cell = world[GRID_WIDTH - 1, GRID_HEIGHT - 1] ?: return

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        assertThat(neighbourCount, _is(3))
    }

    @Test
    fun count_cellAtTopEdge_hasFiveNeighbours() {

        // Fetch cell @ (0, 4)
        val cell = world[0, 4] ?: return

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        // Cell @ (0, 4) has 5 neighbours (3, 0) (3, 1) (4, 1) (5, 1) and (5, 0)
        assertThat(neighbourCount, _is(5))
    }

    @Test
    fun count_cellAtLeftEdge_hasFiveNeighbours() {

        // Fetch cell @ (4, 0)
        val cell = world[4, 0] ?: return

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        // Cell @ (4, 0) has 5 neighbours (0, 3) (1, 3) (1, 4) (1, 5) and (0, 5)
        assertThat(neighbourCount, _is(5))
    }

    @Test
    fun count_cellAtRightEdge_hasFiveNeighbours() {

        val cell = world[GRID_WIDTH - 1, 3] ?: return

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        assertThat(neighbourCount, _is(5))
    }

    @Test
    fun count_cellAtBottomEdge_hasFiveNeighbours() {

        val cell = world[3, GRID_HEIGHT - 1] ?: return

        // Count the neighbours
        val neighbourCount = cell.neighbours.count()

        assertThat(neighbourCount, _is(5))
    }


    //======= Test Initialization Performance =======
    @Test
    fun performance_worldInitialization() {
        val count = 100
        val size = 100

        var totalTime = 0L
        repeat(count) {
            val time = measureTimeMillis {
                val wrld = World(size, size)
                wrld.initialize()
            }

//            println("Time take to initialize the World : $time")

            totalTime += time
        }

        val avgTime = totalTime / count

        print("Average time taken to Initialize a $size x $size World for $count times : $avgTime millis")
    }


    //======= Test Reaction of the World =======
    @Test
    fun reaction() {

        // Here we have a 10 x 10 World

        // Configure the cells for Testing the Reaction

        // === Alive cell with less than 2 neighbours should die
        // Make a cell @ (5, 0) Alive
        val aliveCellWithLessThanTwoNeighbours = world[5, 0] ?: return
        aliveCellWithLessThanTwoNeighbours.state = State.Alive
        // Give it a single Alive neighbour
        world[4, 0]?.state = State.Alive


        // === Alive cell with exactly 2 neighbours ;ives on to the next generation
        // Make cell @ (2, 2) ALive
        val aliveCellWithExactlyTwoNeighbours = world[2, 2] ?: return
        aliveCellWithExactlyTwoNeighbours.state = State.Alive
        // Give it two Alive neighbours
        world[1, 2]?.state = State.Alive
        world[2, 3]?.state = State.Alive

        // === Dead cell with exactly 3 neighbours becomes Alive
        // Make a cell at (7, 3) Dead
        val deadCellWithThreeNeighbours = world[7, 3] ?: return
        deadCellWithThreeNeighbours.state = State.Dead
        // Give it three Alive neighbours
        world[6, 3]?.state = State.Alive // Left
        world[7, 4]?.state = State.Alive // Bottom
        world[8, 2]?.state = State.Alive // Top-Right

        // === Alive cell with more than 3 neighbours dies
        // Make a cell at (4, 7) Alive
        val aliveCellWithMoreThanThreeNeighbours = world[4, 7] ?: return
        aliveCellWithMoreThanThreeNeighbours.state = State.Alive
        // Give it four Alive neighbours
        world[3, 7]?.state = State.Alive // Left
        world[4, 6]?.state = State.Alive // Top
        world[5, 6]?.state = State.Alive // Top-Right
        world[4, 8]?.state = State.Alive // Bottom

        // === Dead cell with more than 3 alive neighbours remain dead
        // Make a cell at (4, 7) Alive
        val deadCellWithMoreThanThreeNeighbours = world[8, 8] ?: return
        deadCellWithMoreThanThreeNeighbours.state = State.Dead
        // Give it four Alive neighbours
        world[7, 8]?.state = State.Alive // Left
        world[7, 7]?.state = State.Alive // Top-Left
        world[9, 7]?.state = State.Alive // Top-Right
        world[7, 9]?.state = State.Alive // Bottom-Left


        // Make the world react
        world.react()

        // === Assertions
        // Alive cell with less than 2 neighbours should die
        assertThat(aliveCellWithLessThanTwoNeighbours.state, _is(State.Dead))

        // Alive cell with exactly 2 neighbours lives on to the next generation
        assertThat(aliveCellWithExactlyTwoNeighbours.state, _is(State.Alive))

        // Dead cell with exactly 3 neighbours becomes Alive
        assertThat(deadCellWithThreeNeighbours.state, _is(State.Alive))

        // Alive cell with more than 3 neighbours dies
        assertThat(aliveCellWithMoreThanThreeNeighbours.state, _is(State.Dead))

        // Dead cell with more than 3 neighbours remains Dead
        assertThat(deadCellWithMoreThanThreeNeighbours.state, _is(State.Dead))

    }

}