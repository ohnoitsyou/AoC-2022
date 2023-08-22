package dev.dayoung

typealias InstPair = Pair<Ten.CPU.Instructions, Int>
typealias RegisterList = Map<String, Int>

class Ten(sampleMode: Boolean) {
    class CPU(private var instructions: List<InstPair>) {
        enum class Instructions {
            NOOP, ADDX
        }

        private val registers = mutableMapOf("x" to 1)
        var cycleCount = 0
        private var subCycle = 0
        private var current: InstPair = instructions.first()

        private fun signalStrength(): RegisterList =
            this.registers.map { (k, v) -> k to v * cycleCount }.toMap()

        fun hasInstructions(): Boolean = instructions.isNotEmpty()
        fun cycle(): RegisterList {
            val range = (registers["x"]!! - 1) .. (registers["x"]!! + 1)
            if((cycleCount % 40) in range) print("#") else print(" ")
            cycleCount++
            if(cycleCount % 40 == 0) println()
            val midCycleRegisters = signalStrength()
            if (hasInstructions()) {
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
            return midCycleRegisters
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
        var sum = 0
        while(cpu.hasInstructions()) {
            val registers = cpu.cycle()
            if(cpu.cycleCount in listOf(20, 60, 100, 140, 180, 220)) {
                sum += registers["x"]!!
            }
        }
        println("Signal Strength: $sum")
    }
}