package dev.dayoung

import kotlin.math.abs

typealias Point = Pair<Int, Int>
class Nine(sampleMode: Boolean) {
    enum class Direction {
        U, D, L, R
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
    fun solve() {
        part1()
    }
}
