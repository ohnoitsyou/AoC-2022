package dev.dayoung

class Three(private val sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode, "three.txt")
    private var i = 0
    private val values = ('a'..'z').associateWith { ++i } + ('A'..'Z').associateWith { ++i }

    private fun part1() {
        var priorityValue = 0
        for(sack in content) {
            val c1 = sack.substring(0, sack.length / 2).toSortedSet()
            val c2 = sack.substring(sack.length / 2, sack.length).toSortedSet()
            priorityValue += values[c1.intersect(c2).first()]!!
        }
        println("Priority: $priorityValue")
    }

    private fun part2() {
        val groups = content.windowed(3, 3)
        var priorityValue = 0
        for(group in groups) {
            priorityValue += values[group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet()).first()]!!
        }
        println("Grouped priority value: $priorityValue")
    }

    fun solve() {
        part1()
        part2()
    }
}