package dev.dayoung

import kotlin.math.sign

typealias InstPair = Pair<Ten.CPU.Instructions, Int>
typealias RegisterList = Map<String, Int>
class Ten(sampleMode: Boolean) {
    class CPU(private var instructions: List<InstPair>) {
        enum class Instructions {
            NOOP, ADDX
        }

        private val registers = mutableMapOf("x" to 1)
        private var cycleCount = 0
        private var subCycle = 0
        private var current: InstPair = instructions.first()

        private fun signalStrength(): RegisterList =
            this.registers.map { (k, v) -> k to v * cycleCount }.toMap()

        fun hasInstructions(): Boolean = instructions.isNotEmpty()
        fun cycle(): RegisterList {
            if (instructions.isNotEmpty()) {
                cycleCount++
                when(current.first) {
                    Instructions.NOOP -> {
                        instructions = instructions.drop(1)
                        current = if(hasInstructions()) instructions.first() else current
                    }
                    Instructions.ADDX -> {
                        if (subCycle == 1) {
                            registers["x"] = registers["x"]!! + current.second
                            subCycle = 0
                            instructions = instructions.drop(1)
                            current = if(hasInstructions()) instructions.first() else current
                        } else {
                            subCycle++
                        }
                    }
                }
            }
            return signalStrength()
        }
        override fun toString(): String {
            return "CPU: Cycle: $cycleCount; SubCycle: $subCycle Registers: $registers"
        }
    }
    private val content = Utils.readInputResource(sampleMode, "ten.txt")
    private val instructions = content.mapNotNull {
        when {
            it.startsWith("noop") -> CPU.Instructions.NOOP to 0
            it.startsWith("addx") -> CPU.Instructions.ADDX to it.split(" ")[1].toInt()
            else -> null
        }
    }
    fun solve() {
        val cpu = CPU(instructions)
        while(cpu.hasInstructions()) {
            cpu.cycle()
            println(cpu)
        }
    }
}