package minesweeper


class MineSweeper(fieldSize: Int = 9, private val numberOfMines: Int) {
    private var hasExecutedFirstExploreAction = false
    private val mineField = MineField(fieldSize, this)
    val gameState: GameState
        get() = when {
            !hasExecutedFirstExploreAction -> GameState.IN_PROGRESS
            mineField.cells.flatten().any { cell -> cell.isMineExplored } -> GameState.LOST
            mineField.cells.flatten().none { cell -> cell.isMineUnmarked || cell.isSafeMarked } -> GameState.WON
            mineField.cells.flatten().filter { cell -> !cell.isExplored }.size == numberOfMines -> GameState.WON
            else -> GameState.IN_PROGRESS
        }

    init {
        mineField.display()
    }

    fun executePlayerAction(i: Int, j: Int, actionType: String) {
        when (actionType) {
            ActionType.MINE.inputName -> mark(i, j)
            ActionType.FREE.inputName -> {
                explore(i, j)
            }
            else -> println("${ActionType.NULL.message}: $actionType")
        }
        mineField.display()
    }

    private fun mark(i: Int, j: Int) {
        val cellToMark = mineField.cell(i, j)
        cellToMark.isMarked = !cellToMark.isMarked
    }

    private fun explore(i: Int, j: Int) {
        if (!hasExecutedFirstExploreAction) {
            hasExecutedFirstExploreAction = true
            mineField.setFirstExploredIndices(i, j)
            placeMines()
        }
        exploreRecursively(i, j)
    }

    private fun exploreRecursively(i: Int, j: Int) {
        val cellToExplore = mineField.cell(i, j)
        
        if (cellToExplore.isExplored) return
        cellToExplore.isExplored = true

        if (cellToExplore.isMined) return

        if (cellToExplore.neighbouringMines == 0) {
            val neighbouringIndices = mineField.getNeighbouringIndices(i, j)
            neighbouringIndices.forEach { (k, l) -> exploreRecursively(k, l) }
        }
    }

    private fun placeMines() {
        repeat(numberOfMines) {
            val (i, j) = mineField.getRandomCellIndices()
            mineField.cell(i, j).isMined = true
            incrementNeighbouringMinesCount(i, j)
        }
    }

    private fun incrementNeighbouringMinesCount(i: Int, j: Int) {
        val neighbouringIndices = mineField.getNeighbouringIndices(i, j)
        neighbouringIndices.forEach { (x, y) ->
            ++mineField.cell(x, y).neighbouringMines
        }
    }
}


enum class GameState(val message: String) {
    IN_PROGRESS("Set/unset mine marks or claim a cell as free: > "),
    LOST("You stepped on a mine and failed!"),
    WON("Congratulations! You found all the mines!"),
}


enum class ActionType(val inputName: String, val message: String) {
    MINE("mine", ""),
    FREE("free", ""),
    NULL("null", "Invalid Action Type"),
}
