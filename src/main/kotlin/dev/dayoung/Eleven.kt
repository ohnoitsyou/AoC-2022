package dev.dayoung

import kotlin.math.floor

fun List<String>.toMonkey(): Eleven.Monkey {
    val number = this[0].substring("Monkey ".length, this[0].length - 1).toInt()
    val items = this[1].substring(this[1].lastIndexOf(':') + 1).split(",").map { it.trim().toInt() }
    val operation = this[2].substring(this[2].lastIndexOf('=') + 2).split(" ").map { it.trim()}
    val test = this[3].substring(this[3].lastIndexOf(" ")).trim().toInt()
    val t = this[4].substring(this[4].lastIndexOf(" ")).trim().toInt()
    val f = this[5].substring(this[5].lastIndexOf(" ")).trim().toInt()
    return Eleven.Monkey(number, items.toMutableList(), Eleven.Operation(operation[0], operation[1], operation[2]), Eleven.Test(test, t, f))
}

fun String.isNumeric() = this.toIntOrNull() != null
fun String.operatorToIntOp(): Int.(Int) -> Int = when (this) {
    "+" -> Int::plus
    "-" -> Int::minus
    "*" -> Int::times
    "/","divisible" -> Int::div
    else -> error("Unknown operator $this")
}
class Eleven(sampleMode: Boolean) {
    class Operation(val factor1: String, val operator: String, val factor2: String) {
    }
    class Test(val divisor: Int, val t: Int, val f: Int) { }
    class Monkey(val number: Int, val items: MutableList<Int>, val operation: Operation, val test: Test) {
        var inspectCount = 0
        fun receiveItem(item: Int) {
            items.add(item)
        }

        fun inspectItems(): List<Pair<Int, Int>> {
            val returnItems = mutableListOf<Pair<Int, Int>>()
            for(item in items) {
                println("Inspecting item with a worry level of $item")
                inspectCount++
                val new = floor(
                    (if (operation.factor2.isNumeric()) {
                        operation.operator.operatorToIntOp()(item, operation.factor2.toInt())
                    } else {
                        operation.operator.operatorToIntOp()(item, item)
                    } / 3).toDouble()
                ).toInt()
                println("New worry level: $new")

                if((new % test.divisor) == 0) {
                    println("divisible by ${test.divisor}")
                    returnItems.add(test.t to new)
                    println("item with worry level $new is thrown to ${test.t}")
                } else {
                    println("not divisible by ${test.divisor}")
                    returnItems.add(test.f to new)
                    println("item with worry level $new is thrown to ${test.f}")
                }
                println("========")
            }
            items.clear()
            return returnItems
        }
    }

    private val content = Utils.readInputResource(sampleMode, "eleven.txt")
    fun part1(monkeys: List<Monkey>) {
        for(i in 0 until 20) {
            for(monkey in monkeys) {
                val thrownItems = monkey.inspectItems()
                thrownItems.forEach { monkeys[it.first].receiveItem(it.second) }
            }
        }
        monkeys.forEachIndexed { idx, monkey -> println("Monkey $idx inspected items ${monkey.inspectCount} times.")}
        val topMonkeys = monkeys.sortedBy { it.inspectCount }.reversed().take(2)
        val monkeyBusiness = topMonkeys.map { it.inspectCount } .reduce { acc, i -> acc * i }
        println("Amount of monkey business: $monkeyBusiness")
    }
    fun solve() {
        val monkeys = content.map{ it.trim() }.filter { it != "" }.chunked(6).map { it.toMonkey() }
        part1(monkeys)
    }
}