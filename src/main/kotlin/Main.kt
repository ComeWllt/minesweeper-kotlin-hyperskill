package minesweeper


fun main() {

    print("How many mines do you want on the field? > ")
    val numberOfMines = readln().toInt()

    val mineSweeper = MineSweeper(numberOfMines = numberOfMines)

    while (mineSweeper.gameState == GameState.IN_PROGRESS) {
        print(mineSweeper.gameState.message)
        val (x, y, actionType) = readln().split(" ")
        val (i, j) = listOf(y, x).map { it.toInt() - 1 }
        mineSweeper.executePlayerAction(i, j, actionType)
    }
    println(mineSweeper.gameState.message)
}
