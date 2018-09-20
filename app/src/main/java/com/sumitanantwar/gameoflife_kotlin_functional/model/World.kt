package com.sumitanantwar.gameoflife_kotlin_functional.model


class World(val gridWidth: Int, val gridHeight: Int) {
    // Array for Cells
    var cells = mutableListOf<Cell>()

    /**
     * Fetches the cell at position (x, y)
     * Here we override the subscript operator
     * so that we can fetch the cells simply by calling **world[[x, y]]**
     */
    operator fun get(x: Int, y: Int): Cell? {
        if ((x in 0 until gridWidth) && (y in 0 until gridHeight)) {
            val cellIndex = (gridWidth * y) + x
            if (cellIndex in 0 until cells.size) {
                return cells[cellIndex]
            }
        }

        return null
    }


    /**
     * Initializes the World
     */
    fun initialize() {

        // Internal function to "Iteratively" pre-fetch all the neighbours of the Cell
        // We already know that the cell can have max of 8 neighbours
        // This gives us a (cellCount * 8) = O(8n) = O(n) time complexity
        fun neighboursForCell(cell: Cell) : List<Cell> {
            val neighbouringCells = mutableListOf<Cell>()

            // All the neighbours of the cell lie in the range of 1 cell before and 1 cell after itself
            // in X as well as Y
            // (-1..1)
            for (dy in -1..1) {
                for (dx in -1..1) {
                    val neighbour = this[cell.x + dx, cell.y + dy]
                    // While iterating we should ignore the current cell itself
                    if ((neighbour != null) && (neighbour != cell)) {
                        neighbouringCells.add(neighbour)
                    }
                }
            }

            return neighbouringCells
        }

        // Clear the cells if they are already populated
        cells.clear()

        // Polulate the cells
        for (y in 0 until gridHeight) {
            for (x in 0 until gridWidth) {
                val cell = Cell(x, y)

                cells.add(cell)
            }
        }

        // Prefetch the neighbours so that we can later apply filters directly
        cells.forEach {
            it.neighbours = neighboursForCell(it)
        }
    }

    /**
     * Make the worls React to the current state
     */
    fun react() {

        // Internal function to count alive neighbours of the cell
        fun aliveNeighboursCountforCell(cell: Cell) : Int {
            return cell.neighbours.filter { it.state == State.Alive }.count()
        }

        // Filter all the Alive Cells
        val aliveCells  = cells.filter { it.state == State.Alive }
        // Filter all the Dead Cells
        val deadCells   = cells.filter { it.state != State.Alive }


        // Dying Cells
        // Rule : Alive cell with Neighbour count < 2 || > 3 Dies
        val dyingCells = aliveCells.filter { cell ->
            val aliveNeighbourCount = aliveNeighboursCountforCell(cell)
            ((aliveNeighbourCount < 2) || (aliveNeighbourCount > 3))
        }

        // Incarnating Cells
        // Rule : Dead cells with Neighbour count == 3 become Alive
        val incarnatingCells = deadCells.filter { aliveNeighboursCountforCell(it) == 3 }


        // All other cells remain unchanged
        // Rule : Cells Alive or Dead with Neighbours == 2, dont change State
        // =======

        // Apply the new State
        // Make all the incarnating cells Alive
        incarnatingCells.forEach{
            it.state = State.Alive
        }

        // Make all the dying cells Dead
        dyingCells.forEach {
            it.state = State.Dead
        }
    }

    //======= World PreConfigurations =======

    /**
     * Make 10% of the cells Alive
     */
    fun preConfigureRandomTenPercent() {
        repeat(cells.size / 10) {
            val x = (0 until gridWidth).shuffled().last()
            val y = (0 until gridHeight).shuffled().last()

            this[x, y]?.state = State.Alive
        }
    }

    /**
     * Setup for Glider Gun Configuration
     */
    fun preConfigureForGliderGun() {
        this[1, 5]?.state = State.Alive
        this[2, 5]?.state = State.Alive
        this[1, 6]?.state = State.Alive
        this[2, 6]?.state = State.Alive

        this[13, 3]?.state = State.Alive
        this[14, 3]?.state = State.Alive
        this[12, 4]?.state = State.Alive
        this[16, 4]?.state = State.Alive
        this[11, 5]?.state = State.Alive
        this[17, 5]?.state = State.Alive
        this[11, 6]?.state = State.Alive
        this[15, 6]?.state = State.Alive
        this[17, 6]?.state = State.Alive
        this[18, 6]?.state = State.Alive
        this[11, 7]?.state = State.Alive
        this[17, 7]?.state = State.Alive
        this[12, 8]?.state = State.Alive
        this[16, 8]?.state = State.Alive
        this[13, 9]?.state = State.Alive
        this[14, 9]?.state = State.Alive

        this[25, 1]?.state = State.Alive
        this[23, 2]?.state = State.Alive
        this[25, 2]?.state = State.Alive
        this[21, 3]?.state = State.Alive
        this[22, 3]?.state = State.Alive
        this[21, 4]?.state = State.Alive
        this[22, 4]?.state = State.Alive
        this[21, 5]?.state = State.Alive
        this[22, 5]?.state = State.Alive
        this[23, 6]?.state = State.Alive
        this[25, 6]?.state = State.Alive
        this[25, 7]?.state = State.Alive

        this[35, 3]?.state = State.Alive
        this[36, 3]?.state = State.Alive
        this[35, 4]?.state = State.Alive
        this[36, 4]?.state = State.Alive
    }
}