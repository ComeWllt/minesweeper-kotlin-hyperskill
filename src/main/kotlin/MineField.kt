package minesweeper


class MineField(private val fieldSize: Int, mineSweeper: MineSweeper) {
    val cells = List(fieldSize) { List(fieldSize) { Cell(mineSweeper) } }
    private val cellIndexPairs = MutableList(fieldSize * fieldSize) {
        listOf(it / fieldSize, it % fieldSize)
    }

    fun cell(i: Int, j: Int) = cells[i][j]

    fun getRandomCellIndices(): List<Int> {
        val randomFieldIndexPair = cellIndexPairs.random()
        cellIndexPairs.remove(randomFieldIndexPair)
        return randomFieldIndexPair
    }

    fun setFirstExploredIndices(i: Int, j: Int) {
        cellIndexPairs.remove(listOf(i, j))
    }

    fun getNeighbouringIndices(i: Int, j: Int) = listOf(
        listOf(i, j + 1), listOf(i + 1, j),
        listOf(i + 1, j + 1), listOf(i, j - 1),
        listOf(i - 1, j), listOf(i - 1, j - 1),
        listOf(i - 1, j + 1), listOf(i + 1, j - 1),
    ).filter { (x, y) -> areIndicesWithinField(x, y) }

    private fun areIndicesWithinField(i: Int, j: Int) = i in 0 until fieldSize
            && j in 0 until fieldSize

    fun display() {
        println(" │123456789│")
        println("—│—————————│")
        for (i in 0 until fieldSize) {
            println("${i + 1}│${cells[i].joinToString("")}│")
        }
        println("—│—————————│")
    }
}
