package dev.dayoung

class Six(sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode, "six.txt")
    private fun findUniqueSequence(line: String, windowSize: Int): Int {
        val windows = line.windowed(windowSize)
        for ((idx, window) in windows.withIndex()) {
            if (window.toSet().size == windowSize) {
                return idx + windowSize
            }
        }
        return 0
    }
    private fun part1() {
        for(entry in content) {
            println("Part 1: ${findUniqueSequence(entry,4)}")
        }
    }
    private fun part2() {
        for(entry in content) {
            println("Part 2: ${findUniqueSequence(entry, 14)}")
        }
    }
    fun solve() {
        part1()
        part2()
    }
}
