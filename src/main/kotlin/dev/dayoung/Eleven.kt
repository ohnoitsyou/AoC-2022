package dev.dayoung

import java.math.BigInteger

fun List<String>.toMonkey(): Eleven.Monkey {
    val number = this[0].substring("Monkey ".length, this[0].length - 1).toInt()
    val items = this[1].substring(this[1].lastIndexOf(':') + 1).split(",").map { it.trim().toBigInteger() }
    val operation = this[2].substring(this[2].lastIndexOf('=') + 2).split(" ").map { it.trim()}
    val test = this[3].substring(this[3].lastIndexOf(" ")).trim().toBigInteger()
    val t = this[4].substring(this[4].lastIndexOf(" ")).trim().toInt()
    val f = this[5].substring(this[5].lastIndexOf(" ")).trim().toInt()
    return Eleven.Monkey(number, items.toMutableList(), Eleven.Operation(operation[0], operation[1], operation[2]), Eleven.Test(test, t, f))
}

fun String.isNumeric() = this.toIntOrNull() != null
fun String.operatorToIntOp(): BigInteger.(BigInteger) -> BigInteger = when (this) {
    "+" -> BigInteger::plus
    "-" -> BigInteger::minus
    "*" -> BigInteger::times
    "/","divisible" -> BigInteger::div
    else -> error("Unknown operator $this")
}
class Eleven(sampleMode: Boolean) {
    data class Operation(val factor1: String, val operator: String, val factor2: String)
    data class Test(val divisor: BigInteger, val t: Int, val f: Int)
    data class Monkey(val number: Int, val items: MutableList<BigInteger>, val operation: Operation, val test: Test) {
        var inspectCount = BigInteger("0")
        fun receiveItem(item: BigInteger) {
            items.add(item)
        }

        fun inspectItems(relief: Boolean = true, gcd: Int): List<Pair<Int, BigInteger>> {
            val returnItems = mutableListOf<Pair<Int, BigInteger>>()
            items.forEach {
                inspectCount++
                val item = if (operation.factor2.isNumeric()) operation.factor2.toBigInteger() else it
                var newWorry = operation.operator.operatorToIntOp()(it,item) % gcd.toBigInteger()

                if(relief) {
                    newWorry /= BigInteger("3")
                }

                if((newWorry % test.divisor).toInt() == 0) {
                    returnItems.add(test.t to newWorry)
                } else {
                    returnItems.add(test.f to newWorry)
                }
            }
            items.clear()
            return returnItems
        }
    }

    private val content = Utils.readInputResource(sampleMode, "eleven.txt")
    private fun doSolve(monkeys: List<Monkey>, rounds: Int, relief: Boolean = true, gcd: Int) {
        for(i in 1 .. rounds) {
            for(monkey in monkeys) {
                val thrownItems = monkey.inspectItems(relief, gcd)
                thrownItems.forEach { monkeys[it.first].receiveItem(it.second) }
            }
            if(relief) {
                println("After round $i:")
                monkeys.forEachIndexed { idx, it -> println("Monkey $idx ${it.items}")}
            }
        }
        val topMonkeys = monkeys.sortedByDescending { it.inspectCount }.take(2)
        val monkeyBusiness = topMonkeys.map { it.inspectCount }.reduce { acc, i -> acc * i }
        println("Amount of monkey business: $monkeyBusiness")
    }
    fun solve() {
        val monkeys1 = content.map{ it.trim() }.filter { it != "" }.chunked(6).map { it.toMonkey() }
        val monkeys2 = content.map{ it.trim() }.filter { it != "" }.chunked(6).map { it.toMonkey() }

        // Chinese Remainder Theorem
        val gcd = monkeys1.fold(1) { acc, monkey -> acc * monkey.test.divisor.toInt()  }
//        doSolve(monkeys1.toList(), 20, true, gcd)
        doSolve(monkeys2.toList(), 10000, false, gcd)
    }
}