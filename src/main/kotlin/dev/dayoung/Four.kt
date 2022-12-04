package dev.dayoung

class Four(sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode, "four.txt")
    private val ranges = computeRanges()

    private fun computeRanges(): List<Pair<IntRange, IntRange>> {
        val ranges = mutableListOf<Pair<IntRange, IntRange>>()
        for (line in content) {
            val elfPairs = line.split(',')
            val firstElfPair = elfPairs[0].split("-")
            val secondElfPair = elfPairs[1].split("-")
            ranges.add(firstElfPair[0].toInt()..firstElfPair[1].toInt() to secondElfPair[0].toInt()..secondElfPair[1].toInt())
        }
        return ranges
    }
    fun solve() {
        var fullyContainedCount = 0
        var partiallyContainedCount = 0
        for (range in ranges) {
            if (range.first.all { range.second.contains(it) } || range.second.all { range.first.contains(it) }) {
                fullyContainedCount++
            }
        }
        println("Fully Contained Count: $fullyContainedCount")
        for(range in ranges) {
            if(range.first.any { range.second.contains(it) } || range.second.any { range.first.contains(it)}) {
                partiallyContainedCount++
            }
        }
        println("Partially Contained Count: $partiallyContainedCount")
    }
}