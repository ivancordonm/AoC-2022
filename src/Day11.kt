fun main() {

    data class Monkey(
        var items: MutableList<Long>,
        val operation: String,
        val opValue: String,
        val div: Long,
        val ifTrue: Int,
        val ifFalse: Int,
        var itemsRevised: Long
    )

    fun buildMonkeys(input: List<String>): List<Monkey> = buildList {
        for (info in input) {
            val monkeyInfo = info.split("\n").drop(1)
            val items = "(\\d+),?".toRegex().findAll(monkeyInfo[0]).map { it.groupValues[1].toLong() }.toList()
            val operation = "([*+]) (\\d+|old)".toRegex().find(monkeyInfo[1])!!.groupValues.toList()
            val divisible = "(\\d+),?".toRegex().findAll(monkeyInfo[2]).map { it.value }.first().toLong()
            val trueToMonkey = "(\\d+)".toRegex().findAll(monkeyInfo[3]).map { it.value }.first().toInt()
            val falseToMonkey = "(\\d+)".toRegex().findAll(monkeyInfo[4]).map { it.value }.first().toInt()

            add(
                Monkey(
                    mutableListOf(*items.toTypedArray()),
                    operation[1],
                    operation[2],
                    divisible,
                    trueToMonkey,
                    falseToMonkey,
                    0
                )
            )
        }
    }

    fun countItems(monkeys: List<Monkey>, iterations: Int, reduce: Int): List<Long> {
        val base = monkeys.map { it.div }.reduce { acc, i -> acc * i }
        repeat(iterations) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.itemsRevised++
                    val value = when (monkey.opValue) {
                        "old" -> item
                        else -> monkey.opValue.toLong()
                    }
                    val total = when (monkey.operation) {
                        "*" -> (item * value) / reduce
                        "+" -> (item + value) / reduce
                        else -> item
                    }
                    if (total % monkey.div == 0L) {
                        monkeys[monkey.ifTrue].items.add(total % base)
                    } else {
                        monkeys[monkey.ifFalse].items.add(total % base)
                    }
                }
                monkey.items = mutableListOf()
            }
        }

        return monkeys.map { it.itemsRevised }

    }

    fun part1(monkeys: List<Monkey>): Long {
        val counter = countItems(monkeys, 20, 3)
        return counter.sortedDescending().take(2).reduce{acc, elem -> acc * elem }
    }

    fun part2(monkeys: List<Monkey>): Long {
        val counter = countItems(monkeys, 10000, 1)
        return counter.sortedDescending().take(2).reduce{acc, elem -> acc * elem }
    }


    val testInput = readInputByGroups("Day11_test")
    val input = readInputByGroups("Day11")


    check(part1(buildMonkeys(testInput)) == 10605L)
    println(part1(buildMonkeys(input)))


    check(part2(buildMonkeys(testInput)) == 2713310158L)
    println(part2(buildMonkeys(input)))

}
