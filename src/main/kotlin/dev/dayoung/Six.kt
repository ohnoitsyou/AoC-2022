package dev.dayoung

class Six(sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode, "six.txt")
    private fun findMarker(line: String, windowSize: Int): Int =
        line.windowed(windowSize).indexOfFirst { it.toSet().size == windowSize } + windowSize

    private fun part1() {
        for(entry in content) {
            println("Part 1: ${findMarker(entry,4)}")
        }
    }
    private fun part2() {
        for(entry in content) {
            println("Part 2: ${findMarker(entry, 14)}")
        }
    }
    fun solve() {
        part1()
        part2()
    }
}
