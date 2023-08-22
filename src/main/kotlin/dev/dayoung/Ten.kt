package dev.dayoung

typealias InstPair = Pair<Ten.CPU.Instructions, Int>

class Ten(sampleMode: Boolean) {
    class CPU(private var instructions: List<InstPair>) {
        enum class Instructions {
            NOOP, ADDX
        }

        private var spriteMidpoint = 1
        var cycleCount = 0
        private var subCycle = 0
        private var currentInstruction = instructions.first()

        private fun signalStrength(): Int = this.spriteMidpoint * cycleCount

        fun hasInstructions(): Boolean = instructions.isNotEmpty()

        fun cycle(): Int {
            val range = (spriteMidpoint - 1) .. (spriteMidpoint + 1)
            if((cycleCount % 40) in range) print("#") else print(" ")
            cycleCount++
            if(cycleCount % 40 == 0) println()
            val midpointStrength = signalStrength()
            if (hasInstructions()) {
                when(currentInstruction.first) {
                    Instructions.NOOP -> {
                        instructions = instructions.drop(1)
                        currentInstruction = if(hasInstructions()) instructions.first() else currentInstruction
                    }
                    Instructions.ADDX -> {
                        if (subCycle == 1) {
                            spriteMidpoint +=  currentInstruction.second
                            subCycle = 0
                            instructions = instructions.drop(1)
                            currentInstruction = if(hasInstructions()) instructions.first() else currentInstruction
                        } else {
                            subCycle++
                        }
                    }
                }
            }
            return midpointStrength
        }
        override fun toString(): String {
            return "CPU: Cycle: $cycleCount; SubCycle: $subCycle Registers: $spriteMidpoint"
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
        var sum = 0
        while(cpu.hasInstructions()) {
            val signalStrength = cpu.cycle()
            if(cpu.cycleCount in listOf(20, 60, 100, 140, 180, 220)) {
                sum += signalStrength
            }
        }
        println("Signal Strength: $sum")
    }
}