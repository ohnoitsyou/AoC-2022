package dev.dayoung

import kotlin.math.abs

typealias Point = Pair<Int, Int>
class Nine(sampleMode: Boolean) {

    class Rope(knotCount: Int) {
        enum class Direction {
            U, D, L, R
        }
        var knots = mutableListOf<Point>()
        val tailHistory = mutableSetOf<Point>()
        init {
            knots = List(knotCount) { 0 to 0 }.toMutableList()
        }

        fun headStep(direction: Direction): Point {
            val knot = knots[0]
            return when (direction) {
                Direction.U -> knot.first to knot.second + 1
                Direction.D -> knot.first to knot.second - 1
                Direction.L -> knot.first - 1 to knot.second
                Direction.R -> knot.first + 1 to knot.second
            }
        }
        fun knotStep(self: Point, parent: Point): Point {
            val (px, py) = parent
            val (sx, sy) = self
            // Chebyshev Distance ✓
            // 1 1 1
            // 1 * 1
            // 1 1 1
            // Manhattan Distance 𐄂
            // 2 1 2
            // 1 * 1
            // 2 1 2
            val distance = maxOf(abs(px - sx), abs(py - sy))
            if(distance > 1) {
                return when {
                    px == sx -> if (py > sy) sx to sy + 1 else sx to sy - 1 // x is same, move up or down
                    py == sy -> if (px > sx) sx + 1 to sy else sx - 1 to sy // y is same, move left or right
                    px > sx -> if(py > sy) sx + 1 to sy + 1 else sx + 1 to sy - 1 // Up Right / Down Right
                    else -> if(py > sy) sx - 1 to sy + 1 else sx - 1 to sy - 1 // Up Left / Down Left
                }
            }
            return self // No movement necessary
        }
        fun step(instruction: String) {
            val step = instruction.split(" ")
            val direction = Direction.valueOf(step[0])
            val count = step[1].toInt()
            for(i in 0 until count) {
                knots[0] = headStep(direction)
                for(knot in knots.indices.drop(1)) {
                    knots[knot] = knotStep(knots[knot], knots[knot - 1])
                    if(knot == knots.lastIndex) {
                        tailHistory.add(knots[knot])
                    }
                }
            }
        }
    }

    private val content = Utils.readInputResource(sampleMode, "nine.txt")

    private fun part1() {
        val rope = Rope(2)
        content.forEach { rope.step(it) }
        println("Tail locations: ${rope.tailHistory.size}")
    }

    private fun part2() {
        // the head of the rope is still a knot
        val rope = Rope(10)
        content.forEach { rope.step(it) }
        println("Tail count: ${rope.tailHistory.size}")
    }

    fun solve() {
        part1()
        part2()
    }
}
