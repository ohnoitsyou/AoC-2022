package dev.dayoung

typealias InstPair = Pair<Instruction, Int>
class Ten(sampleMode: Boolean) {
    class CPU {
        enum class Instructions {
            NOOP, ADDX
        }
        val registers = mutableMapOf("x" to 1)
        val cycleCount = 0

        fun cycle(inst: InstPair) {
            
        }
    }

    private val content = Utils.readInputResource(sampleMode, "ten.txt")
    private val instructions = content.mapNotNull {
        when {
            it.startsWith("noop") -> CPU.Instructions.NOOP to 0
            it.startsWith("addx") -> CPU.Instructions.ADDX to it.split(" ")[1]
            else -> null
        }
    }
    fun solve() {
        val cpu = CPU()
        for(inst in instructions) {
            cpu.cycle(inst)
        }
    }
}