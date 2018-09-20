#  Game of Life using Functional Kotlin
---

There is a two-dimensional grid of cells which can be either alive or dead. From there we calculate each of the following steps with these rules:

### Rules
1. A living cell with 1 or less neighbors dies.
2. A living cell with 4 or more neighbors dies.
3. A living cell with 2 or 3 neighbors continues living.
4. A dead cell with 3 neighbors starts to live
5. A dead cell with 4 or more neighbors remains dead


### Approach
1. Cells are stored in a Simple One-Dimensional MutableArray. Two-Dimensional arrays are usually a pain to manage.
2. Cell Position is computed by augmenting the Cell Index with the Grid Width

        // Polulate the cells
        for (y in 0 until gridHeight) {
            for (x in 0 until gridWidth) {
                val cell = Cell(x, y)

                cells.add(cell)
            }
        }


3. All the neighbours of the cell are pre-coumputed immediately after populating the Cell Array

        // Prefetch the neighbours so that we can later apply filters directly
        cells.forEach {
            it.neighbours = neighboursForCell(it)
        }

    **Advantages**

    - Once we have the list of neighbours ready at hand, we can easily Count the Alive Neighbours just by filtering the list

            // Internal function to count alive neighbours of the cell
            fun aliveNeighboursCountforCell(cell: Cell) : Int {
                return cell.neighbours.filter { it.state == State.Alive }.count()
            }

    - And then applying all the Rules becomes a breeze

            // Dying Cells
            // Rule : Alive cell with Neighbour count < 2 || > 3 Dies
            val dyingCells = aliveCells.filter { cell ->
                val aliveNeighbourCount = aliveNeighboursCountforCell(cell)
                ((aliveNeighbourCount < 2) || (aliveNeighbourCount > 3))
            }

            // Incarnating Cells
            // Rule : Dead cells with Neighbour count == 3 become Alive
            val incarnatingCells = deadCells.filter { aliveNeighboursCountforCell(it) == 3 }



    - After this changing the State of the World becomes as easy as just iterating over all the filtered cells and setting the new state

            // Apply the new State
            // Make all the incarnating cells Alive
            incarnatingCells.forEach{
                it.state = State.Alive
            }

            // Make all the dying cells Dead
            dyingCells.forEach {
                it.state = State.Dead
            }


    ### UI
    1. The Density of the World is currently set to 50 Columns. Vertical Row Count is computed automatically
    2. Three buttons have been provided
    - Play / Pause Button : Starts / Pauses the animation
    - Reset Button : Resets the World to a Random State, and randomly makes 10% of the cells Alive
    - Glider Button : I have also included the famous Glider Gun configuration. This was important for confirming that the Game of Life is really functioning as per the defined Rules.

    3. Legends
    - White Cell  : Unborn, was never Alive or Dead
    - Blue Cell : Alive
    - Light Gray Cell : Dead


    ### TDD
    1. The App was implemented in a true TDD fasion
    2. All the tests have been implemented in the
    - **WorldTests.kt**
    - **MainPresenterTest.kt**

