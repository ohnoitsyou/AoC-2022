package dev.dayoung

import kotlin.math.floor

fun List<String>.toMonkey(): Eleven.Monkey {
    val number = this[0].substring("Monkey ".length, this[0].length - 1).toInt()
    val items = this[1].substring(this[1].lastIndexOf(':') + 1).split(",").map { it.trim().toDouble() }
    val operation = this[2].substring(this[2].lastIndexOf('=') + 2).split(" ").map { it.trim()}
    val test = this[3].substring(this[3].lastIndexOf(" ")).trim().toInt()
    val t = this[4].substring(this[4].lastIndexOf(" ")).trim().toInt()
    val f = this[5].substring(this[5].lastIndexOf(" ")).trim().toInt()
    return Eleven.Monkey(number, items.toMutableList(), Eleven.Operation(operation[0], operation[1], operation[2]), Eleven.Test(test, t, f))
}

fun String.isNumeric() = this.toIntOrNull() != null
fun String.operatorToIntOp(): Double.(Double) -> Double = when (this) {
    "+" -> Double::plus
    "-" -> Double::minus
    "*" -> Double::times
    "/","divisible" -> Double::div
    else -> error("Unknown operator $this")
}
class Eleven(sampleMode: Boolean) {
    class Operation(val factor1: String, val operator: String, val factor2: String) {
    }
    class Test(val divisor: Int, val t: Int, val f: Int) { }
    class Monkey(val number: Int, val items: MutableList<Double>, val operation: Operation, val test: Test) {
        var inspectCount = 0
        fun receiveItem(item: Double) {
            items.add(item)
        }

        fun inspectItems(relief: Boolean = true): List<Pair<Int, Double>> {
            val returnItems = mutableListOf<Pair<Int, Double>>()
            for(item in items) {
//                println("Inspecting item with a worry level of $item")
                inspectCount++
                var newWorry = if (operation.factor2.isNumeric()) {
                    operation.operator.operatorToIntOp()(item, operation.factor2.toDouble())
                } else {
                    operation.operator.operatorToIntOp()(item, item)
                }
                if(relief) {
                    floor(newWorry)
                    newWorry = floor(newWorry / 3.0)
                }
//                println("New worry level: $newWorry")

                if((newWorry % test.divisor) == 0.0) {
//                    println("divisible by ${test.divisor}")
                    returnItems.add(test.t to newWorry)
//                    println("item with worry level $newWorry is thrown to ${test.t}")
                } else {
//                    println("not divisible by ${test.divisor}")
                    returnItems.add(test.f to newWorry)
//                    println("item with worry level $newWorry is thrown to ${test.f}")
                }
//                println("========")
            }
            items.clear()
            return returnItems
        }
    }

    private val content = Utils.readInputResource(sampleMode, "eleven.txt")
    private fun doSolve(monkeys: List<Monkey>, rounds: Int, relief: Boolean = true) {
        for(i in 0 until rounds) {
            for(monkey in monkeys) {
                val thrownItems = monkey.inspectItems(relief)
                thrownItems.forEach { monkeys[it.first].receiveItem(it.second) }
            }
            monkeys.forEach {
                println("Monkey ${it.number}: ${it.items}")
            }
            if(i + 1 in listOf(1,20,1000,2000,3000,4000,5000,6000,7000,8000,9000,10000)) {
                println("== After round ${i +1} ==")
                monkeys.forEachIndexed { idx, monkey -> println("Monkey $idx inspected items ${monkey.inspectCount} times.")}
            }

        }
        //monkeys.forEachIndexed { idx, monkey -> println("Monkey $idx inspected items ${monkey.inspectCount} times.")}
        val topMonkeys = monkeys.sortedBy { it.inspectCount }.reversed().take(2)
        val monkeyBusiness = topMonkeys.map { it.inspectCount } .reduce { acc, i -> acc * i }
        println("Amount of monkey business: $monkeyBusiness")
    }
    fun solve() {
        val monkeys = content.map{ it.trim() }.filter { it != "" }.chunked(6).map { it.toMonkey() }
//        doSolve(monkeys.toList(), 20)
        doSolve(monkeys.toList(), 20, false)
    }
}