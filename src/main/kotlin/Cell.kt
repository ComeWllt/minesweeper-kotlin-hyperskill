package minesweeper


class Cell(
    private val mineSweeper: MineSweeper,
    var isMined: Boolean = false,
    var isMarked: Boolean = false,
    var isExplored: Boolean = false,
    var neighbouringMines: Int = 0,
) {
    val isMineUnmarked
        get() = isMined && !isMarked
    val isSafeMarked
        get() = !isMined && isMarked
    val isMineExplored
        get() = isMined && isExplored

    override fun toString() = when {
        isMined && mineSweeper.gameState == GameState.LOST -> CellStatus.MINED.symbol
        isExplored && neighbouringMines == 0 -> CellStatus.EXPLORED.symbol
        isExplored && neighbouringMines >= 0 -> neighbouringMines.toString()
        isMarked -> CellStatus.MARKED.symbol
        else -> CellStatus.SAFE.symbol
    }
}


enum class CellStatus(val symbol: String) {
    SAFE("."),
    MARKED("*"),
    EXPLORED("/"),
    MINED("X"),
}
