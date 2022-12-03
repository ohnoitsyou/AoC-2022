package dev.dayoung

class One(private val sampleMode: Boolean = false) {
    private val elves = mutableListOf<Int>()
    init {
        val content = Utils.readInputResource(sampleMode, "one.txt")
        var curCalories = 0
        for(line in content) {
            when(line) {
                "" -> {
                    elves.add(curCalories)
                    curCalories = 0
                }
                else -> {
                    curCalories += line.toInt()
                }
            }
        }
        elves.add(curCalories)
        elves.sortDescending()
    }
    fun solve() {
        if(this.sampleMode) { println("Known Max Calories: 24000") }
        println("Max Calories: ${elves.maxOf { it }}")
        if(this.sampleMode) { println("Known top 3: 45000") }
        println("Top 3: ${elves.subList(0,3).sum()}")
    }
}