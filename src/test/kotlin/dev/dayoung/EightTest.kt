package dev.dayoung

import org.junit.jupiter.api.Test

class EightTest {
    @Test
    fun `3x3 center`() {
        val input = listOf("000", "010", "000")
        val grid = Eight.Grid(input)
        println("0, 0: ${grid.isVisible(0, 0)}")
        println("1, 1: ${grid.isVisible(1, 1)}")
    }

    @Test
    fun `3x3 all`() {
        val input = listOf("111", "111", "111")
        val grid = Eight.Grid(input)
        println("0, 0: ${grid.isVisible(0, 0)}")
        println("1, 1: ${grid.isVisible(1, 1)}")
    }
}