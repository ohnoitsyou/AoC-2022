package dev.dayoung

class Twelve(sampleMode: Boolean) {
    class Grid(input: List<String>) {
        private val height = input.size
        private val width = input[0].length
        private val grid = input.fold("") { acc, line -> acc + line }.map { it.code }
        private val dirs = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
        init {
            println("Grid: ${grid.map { it.toChar() }}")
        }

        private fun cellAt(p: Point): Int? =
            if(p.first in 0 .. width || p.second in 0 .. height) grid[p.first + p.second * height] else null
        fun neighbors(cell: Point): List<Point> {
            return dirs.map { cell.first + it.first to cell.second + it.second }
                .filter { cellAt(it) != null }
                .filter { cellAt(cell) in (cellAt(it)!! - 1) .. (cellAt(it)!! + 1)}
        }

        override fun toString(): String {
            return (0 until height).foldIndexed("") { idy, yacc, y ->
                yacc + (0 until width).foldIndexed("") { idx, xacc, x -> xacc + cellAt(idx to idy)?.toChar() } + "\n"
            }
        }

    }
    private val content = Utils.readInputResource(sampleMode, "twelve.txt")
    fun solve() {
        println(content)
        val grid = Grid(content)
        println(grid)
    }
}