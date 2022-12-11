fun main() {

    class Monkey(
        var items: MutableList<Long>,
        val operation: String,
        val opValue: String,
        val div: Long,
        val ifTrue: Int,
        val ifFalse: Int,
        var intemsRevised: Long
    )

    fun getMonkeysInfo(input: List<String>): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        for (info in input) {
            val monkeyInfo = info.split("\n").drop(1)
            val items = "(\\d+),?".toRegex().findAll(monkeyInfo[0]).map { it.groupValues[1].toLong() }.toList()
            val operation = "(\\*|\\+) (\\d+|old)".toRegex().find(monkeyInfo[1])!!.groupValues.toList()
            val divisible = "(\\d+),?".toRegex().findAll(monkeyInfo[2]).map { it.value }.first().toLong()
            val trueToMonkey = "(\\d+)".toRegex().findAll(monkeyInfo[3]).map { it.value }.first().toInt()
            val falseToMonkey = "(\\d+)".toRegex().findAll(monkeyInfo[4]).map { it.value }.first().toInt()

            monkeys.add(
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
        return monkeys.toList()
    }

    fun part1(input: List<String>): Long {

        val monkeys = getMonkeysInfo(input)
        repeat(20) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.intemsRevised++
                    val value = when (monkey.opValue) {
                        "old" -> item
                        else -> monkey.opValue.toLong()
                    }
                    val total = when (monkey.operation) {
                        "*" -> (item * value) / 3
                        "+" -> (item + value) / 3
                        else -> item
                    }
                    if (total % monkey.div == 0L) {
//                        println("Monkey ${monkeys.indexOf(monkey)}: item $total -> ${monkey.ifFalse}")
                        monkeys[monkey.ifTrue].items.add(total)
                    } else {
//                        println("Monkey ${monkeys.indexOf(monkey)}: item $total -> ${monkey.ifFalse}")
                        monkeys[monkey.ifFalse].items.add(total)
                    }
                }
                monkey.items = mutableListOf()
            }
//            monkeys.forEach { println(it.items) }
        }
//        println(monkeys.map { it.intemsRevised })
        return monkeys.map { it.intemsRevised }.sortedDescending().let { it[0] * it[1] }
    }

    fun part2(input: List<String>): Long {
        val monkeys = getMonkeysInfo(input)
        val common = monkeys.map { it.div }.reduce { acc, elem -> acc * elem }
        repeat(10000) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.intemsRevised++
                    val value = when (monkey.opValue) {
                        "old" -> item
                        else -> monkey.opValue.toLong()
                    }
                    val total = when (monkey.operation) {
                        "*" -> item * value
                        "+" -> item + value
                        else -> item
                    }
                    if (total % monkey.div == 0L) {
//                        println("Monkey ${monkeys.indexOf(monkey)}: item $total -> ${monkey.ifFalse}")
                        monkeys[monkey.ifTrue].items.add(total % common)
                    } else {
//                        println("Monkey ${monkeys.indexOf(monkey)}: item $total -> ${monkey.ifFalse}")
                        monkeys[monkey.ifFalse].items.add(total % common)
                    }
                }
                monkey.items = mutableListOf()
            }
//            monkeys.forEach { println(it.items) }
        }
//        println(monkeys.map { it.intemsRevised })
        return monkeys.map { it.intemsRevised }.sortedDescending().let { it[0] * it[1] }
    }


    val testInput = readInputByGroups("Day11_test")
    val input = readInputByGroups("Day11")


    check(part1(testInput) == 10605L)
    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 2713310158)
    println(part2(input))

}
