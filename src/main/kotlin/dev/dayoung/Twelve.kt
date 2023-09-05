package dev.dayoung

class Twelve(sampleMode: Boolean) {
    class Grid(input: List<String>) : Iterable<Point> {
        private val height = input.size
        private val width = input[0].length
        private val grid = input.fold("") { acc, line -> acc + line }.map { it.code }
        private val dirs = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
        init {
            println("Grid: ${grid.map { it.toChar() }}")
        }

        override fun iterator(): Iterator<Point> {
            return object : Iterator<Point> {
                var x = -1
                var y = -1

                override fun hasNext(): Boolean {
                    return x + y * width < grid.lastIndex
                }

                override fun next(): Point {
                    val hitY = if((x + 1) % width == 0) y + 1 else y
                    y = if((x + 1) % width == 0) y + 1 else y
                    x = (x + 1) % width
                    return x to y
                }
            }
        }

        fun cellAt(p: Point): Int? =
            if(p.first in 0 until width &&
               p.second in 0 until height &&
               (p.first + p.second * width) < grid.size) grid[p.first + p.second * width] else null
        fun neighbors(cell: Point): List<Point> {
            return dirs.map { cell.first + it.first to cell.second + it.second }
                .filter { cellAt(it) != null }
                .filter { cellAt(cell) in (cellAt(it)!! - 1) .. (cellAt(it)!! + 1)}
        }

        fun breadthSearch(grid: Grid, start: Point) {
            val frontier = ArrayDeque<Point>()
            frontier.add(start)
            val reached = HashMap<Point, Boolean>()
            reached[start] = true

            while(frontier.isNotEmpty()) {
                val current = frontier.first()
                println("Visiting: $current")
                for(next in neighbors(current)) {
                    if(!reached.contains(next)) {
                        frontier.add(next)
                        reached[next] = true
                    }
                }
            }
        }

        override fun toString(): String {
            return (0 until height).foldIndexed("") { idy, yacc, y ->
                yacc + (0 until width).foldIndexed("") {
                        idx, xacc, _ -> xacc + cellAt(idx to idy)?.toChar()
                } + "\n"
            }
        }
    }
    private val content = Utils.readInputResource(sampleMode, "twelve.txt")
    fun solve() {
        val grid = Grid(content)
        println(grid)
        var start = 0 to 0
        for(cell in grid) {
            println("$cell ${grid.cellAt(cell)?.toChar()} ${grid.neighbors(cell)}")
            if(grid.cellAt(cell)?.toChar() == 'S') {
               start = cell
            }
        }
        grid.breadthSearch(grid, start)
    }
}