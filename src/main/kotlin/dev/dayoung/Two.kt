package dev.dayoung

/*
Part 1
A = X = Rock
B = Y = Paper
C = Z = Scissors

Part 2
A = Rock; B = Paper; C = Scissors
X = Lose; Y = Tie; Z = Win

  A B C
X C A B
Y A B C
Z B C A
 */

class Two(private val sampleMode: Boolean = false) {
    private val moves: List<String> = Utils.readInputResource(sampleMode, "two.txt")
    private val points = mapOf("A" to 1, "B" to 2, "C" to 3, "X" to 1, "Y" to 2, "Z" to 3, "W" to 6, "L" to 0, "T" to 3)

    private fun part1() {
        val winners = mapOf("A" to "Z", "B" to "X", "C" to "Y")
        val tie = mapOf("A" to "X", "B" to "Y", "C" to "Z")
        val losers = mapOf("A" to "Y", "B" to "Z", "C" to "X")
        var totalPoints = 0
        for(move in moves) {
            val subMove = move.split(" ")
            val elf = subMove[0]
            val you = subMove[1]
            if(tie[elf] == you) {
                println("Tie")
                totalPoints += points[you]!! + points["T"]!!
            }
            if(winners[elf] == you) {
                println("Elf Wins")
                totalPoints += points[you]!! + points["L"]!!
            }
            if(losers[elf] == you) {
                println("You win")
                totalPoints += points[you]!! + points["W"]!!
            }
        }
        println("Total points: $totalPoints")
    }

    private fun part2() {
        val win = mapOf("A" to "B", "B" to "C", "C" to "A")
        val lose = mapOf("A" to "C", "B" to "A", "C" to "B")
        val tie = mapOf("A" to "A", "B" to "B", "C" to "C")
        var totalPoints = 0
        for(move in moves) {
            val subMove = move.split(" ")
            val input = subMove[0]
            val result: Int = when(subMove[1]) {
                "X" -> points["L"]!! + points[lose[input]]!!
                "Y" -> points["T"]!! + points[tie[input]]!!
                "Z" -> points["W"]!! + points[win[input]]!!
                else -> 0
            }
            println("Round: $move ; Resulting points: $result")
            totalPoints += result
        }
        println("Final Score: $totalPoints")
    }

    fun solve() {
        this.part1()
        this.part2()
    }
}