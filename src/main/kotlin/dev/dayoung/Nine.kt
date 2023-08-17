package dev.dayoung

import kotlin.math.abs

typealias Point = Pair<Int, Int>
class Nine(sampleMode: Boolean) {
    enum class Direction {
        U, D, L, R
    }

    class Rope(knotCount: Int) {
        private var knots = mutableListOf<Point>()
        private val tailHistory = mutableSetOf<Point>()
        init {
            knots = List(knotCount) { 0 to 0 }.toMutableList()
        }

        fun headStep(knot: Point, direction: Direction): Point {
            return when (direction) {
                Direction.U -> knot.first to knot.second + 1
                Direction.D -> knot.first to knot.second - 1
                Direction.L -> knot.first - 1 to knot.second
                Direction.R -> knot.first + 1 to knot.second
            }
        }
        fun knotStep(self: Point, parentOldPos: Point, parentNewPos: Point): Point {
            val (hx, hy) = parentNewPos
            val (tx, ty) = self
            val dx = hx - tx
            val dy = hy - ty
            // Chebyshev Distance
            val htDistance = maxOf(abs(dx), abs(dy))
            // Does the current point, self, need to move?
            return if(htDistance > 1) parentOldPos else self
        }
        fun move(direction: Direction, count: Int) {
            for(i in 0 until count) {
                // Save where the head was
                var parentOldPos = knots[0]
                // Move the head of the rope
                knots[0] = headStep(knots[0], direction)
                // Move any of the subsequent knots
                println("Head Move")
                // New position is calculated differently
                // When in same row or column, move directionally
                // otherwise move diagonally
                for((idx, currentKnot) in knots.withIndex().drop(1)) {
                    val parentKnot = knots[idx - 1]
                    val newPosition = knotStep(currentKnot, parentOldPos, parentKnot)
                    // Save new location, even if it's the same
                    knots[idx] = newPosition
                    // Save our old position so the next knot can use it
                    parentOldPos = currentKnot
                    if(direction == Direction.U) printBoard()
                }
                tailHistory.add(knots.last())
            }

            for((idx, knot) in knots.withIndex()) {
                println("$idx : $knot")
            }
            println("Tail history: ${tailHistory.size}")
        }
        fun printBoard() {
            for(y in 20 downTo -20) {
                for( x in -20 .. 20) {
                    val point = x to y
                    if(knots.contains(point)) print(knots.indexOf(x to y))
                    else if(tailHistory.contains(point)) print("#")
                    else print(".")
                }
                println()
            }
            println()
        }
    }

    private val content = Utils.readInputResource(sampleMode, "nine.txt")

    // x to y
    private var head = 0 to 0
    private var tail = 0 to 0
    // Tail visits the origin at the start
    private val tailHistory = mutableSetOf(tail)
    private fun headStep(cmd: String) {
        val step = cmd.split(" ")
        val direction = Direction.valueOf(step[0])
        val count = step[1].toInt()
        for(i in 0 until count) {
            val newHead = when (direction) {
                Direction.U -> head.first to head.second + 1
                Direction.D -> head.first to head.second - 1
                Direction.L -> head.first - 1 to head.second
                Direction.R -> head.first + 1 to head.second
            }
            moveTail(head, newHead)
            head = newHead
        }
    }
    private fun moveTail(oldHead: Point, newHead: Point) {
        val (hx, hy) = newHead
        val (tx, ty) = tail
        val dx = hx - tx
        val dy = hy - ty
        // Chebyshev Distance
        val htDistance = maxOf(abs(dx), abs(dy))
        // When tail moves, it's moving to oldHead
        if(htDistance > 1) {
            tail = oldHead
            tailHistory.add(tail)
        }
    }
    private fun part1() {
        content.forEach { headStep(it) }
        println("Tail locations: ${tailHistory.size}")
    }

    private fun part2() {
        // the head of the rope is still a knot
        val rope = Rope(10)
//        content.first().let {
        //content.forEach {
        content.take(2).forEach {
            println("== $it ==")
            val step = it.split(" ")
            val direction = Direction.valueOf(step[0])
            val count = step[1].toInt()
            rope.move(direction, count)
            rope.printBoard()
        }
    }

    fun solve() {
        //part1()
        part2()
    }
}
