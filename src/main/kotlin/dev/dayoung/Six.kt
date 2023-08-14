package dev.dayoung

class Six(sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode, "six.txt")
    private fun findMarker(line: String, window: Int): Int = line.windowed(window).indexOfFirst { it.toSet().size == window } + window
    private fun part1() = content.forEach { println("Part 1: ${findMarker(it,4)}")}
    private fun part2() = content.forEach { println("Part 2: ${findMarker(it, 14)}") }
    fun solve() {
        part1()
        part2()
    }
}
