package dev.dayoung
/*
  0 1 2 3 4 5
  1
  2
  3   x
  4
  5

y * width + x
 */
/**
 * I happen to know that the grid is square
 */

inline fun <T> Iterable<T>.takeWhileInclusive(
    predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

class Eight(sampleMode: Boolean = false) {

    private val content = Utils.readInputResource(sampleMode, "eight.txt")
    private val grid = Grid(content)
    private val pointList = content.indices.flatMap { x ->
        content.indices.map { y ->
            y to x
        }
    }.filter { // Filter out edge points
            (x, y) -> x != 0 && x != grid.gridWidth - 1 && y != 0 && y != grid.gridHeight - 1
    }

    class Grid(content: List<String>) {
        val gridWidth = content.first().length
        val gridHeight = content.first().length
        val perimeterCount = gridWidth * 2 + gridHeight * 2 - 4 // Don't double count the corners
        private val grid = content.fold("") { acc, value -> "$acc$value" }.toList()

        fun cellAt(x: Int, y: Int): Int {
            return grid[(y * gridWidth) + x].digitToInt()
        }

        fun isVisible(x: Int, y: Int): Boolean {
            // Check the edges
            if(x == 0 || x == gridHeight || y == 0 || y == gridWidth) return true

            val myHeight = this.cellAt(x, y)
            // Check column
            return !((0 until y).any { cellAt(x, it) >= myHeight } &&
                (y + 1 until gridHeight).any { cellAt(x, it) >= myHeight } &&
            // Check Row
                (0 until x).any { cellAt(it, y) >= myHeight } &&
                (x + 1 until gridWidth).any { cellAt(it, y) >= myHeight })
        }

        fun scenicScore(point: Pair<Int, Int>): Int {
            val (x, y) = point
            val myHeight = this.cellAt(x, y)
            // Count column
            return (0 until y).reversed().takeWhileInclusive { cellAt(x, it) < myHeight }.count() *
            (y + 1 until gridHeight).takeWhileInclusive { cellAt(x, it) < myHeight }.count() *
            // Count Row
            (0 until x).reversed().takeWhileInclusive { cellAt(it, y) < myHeight }.count() *
            (x + 1 until gridWidth).takeWhileInclusive { cellAt(it, y) < myHeight }.count()
        }
    }

    private fun part1() {
        val visibleCount = pointList.fold(grid.perimeterCount) { acc, point ->
            if(grid.isVisible(point.first, point.second)) acc + 1 else acc
        }
        println("Visible trees: $visibleCount")
    }

    private fun part2() {
        val scenicScores = pointList.map {
            grid.scenicScore(it)
        }
        println("Max scenic score: ${scenicScores.maxOf { it }}")
    }

    fun solve() {
        println("Day 8")
        part1()
        part2()
    }
}