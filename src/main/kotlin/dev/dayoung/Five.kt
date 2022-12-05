package dev.dayoung

data class Instruction(val quantity: Int, val source: Int, val destination: Int)

class Five(sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode, "five.txt")
        .filter { !it.startsWith("//") }
    private val instructionList = mutableListOf<Instruction>()
    private val instructionRegex = Regex("move (\\d+) from (\\d+) to (\\d+)")
    private var boxes = mutableListOf<MutableList<Char>>()

    init {
        val endOfSetup = content.indexOfFirst { it == "" }
        this.boxes = content.subList(0, endOfSetup).map { it.toMutableList() }.toMutableList()
        val instructions = content.subList(endOfSetup + 1, content.size)
        for(instruction in instructions) {
            val (quantity, source, destination) = instructionRegex.find(instruction)!!.destructured
            instructionList.add(Instruction(quantity.toInt(), source.toInt(), destination.toInt()))
        }
    }

    private fun moveCrates(reverse: Boolean): MutableList<MutableList<Char>> {
        val crates = this.boxes.map { it.toCollection(mutableListOf()) }.toMutableList()
        for(instruction in instructionList) {
            val head = crates[instruction.source - 1].dropLast(instruction.quantity)
            val tail = crates[instruction.source - 1].drop(crates[instruction.source - 1].size - instruction.quantity)
            crates[instruction.source - 1] = head.toMutableList()
            if(reverse) {
                crates[instruction.destination - 1].addAll(tail.reversed())
            } else {
                crates[instruction.destination - 1].addAll(tail)
            }
        }
        return crates
    }

    private fun part1() {
        val crates = moveCrates(true)
        println("Part 1 Final: ${crates.map { it.last() }.joinToString("")}")
    }

    private fun part2() {
        val crates = moveCrates(false)
        println("Part 2 Final: ${crates.map { it.last() }.joinToString("")}")
    }

    fun solve() {
        part1()
        part2()
    }
}