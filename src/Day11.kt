
fun main() {

    class Monkey(
        var items: MutableList<Int>,
        val operation: String,
        val opValue: String,
        val div: Int,
        val ifTrue: Int,
        val ifFalse: Int,
        var intemsRevised: Int
    )

    fun getMonkeysInfo(input: List<String>): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        for (info in input) {
            val monkeyInfo = info.split("\n").drop(1)
            val items = "(\\d+),?".toRegex().findAll(monkeyInfo[0]).map { it.groupValues[1].toInt() }.toList()
            val operation = "(\\*|\\+) (\\d+|old)".toRegex().find(monkeyInfo[1])!!.groupValues.toList()
            val divisible = "(\\d+),?".toRegex().findAll(monkeyInfo[2]).map { it.value }.first().toInt()
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

    fun part1(input: List<String>): Int {

        val monkeys = getMonkeysInfo(input)
        repeat(20) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.intemsRevised++
                    val value = when (monkey.opValue) {
                        "old" -> item
                        else -> monkey.opValue.toInt()
                    }
                    val total = when (monkey.operation) {
                        "*" -> (item * value) / 3
                        "+" -> (item + value) / 3
                        else -> item
                    }
                    if (total.mod(monkey.div) == 0) {
                        println("Monkey ${monkeys.indexOf(monkey)}: item $total -> ${monkey.ifFalse}")
                        monkeys[monkey.ifTrue].items.add(total)
                    } else {
                        println("Monkey ${monkeys.indexOf(monkey)}: item $total -> ${monkey.ifFalse}")
                        monkeys[monkey.ifFalse].items.add(total)
                    }
                }
                monkey.items = mutableListOf()
            }
            monkeys.forEach{ println(it.items) }
        }
        println( monkeys.map { it.intemsRevised })
        return monkeys.map { it.intemsRevised }.sortedDescending().let { it[0] * it [1] }
    }



    val testInput = readInputByGroups("Day11_test")
    val input = readInputByGroups("Day11")


    check(part1(testInput) == 10605)
    println(part1(input))

//    check(part2(testInput) == 2713310158)
//    println(part2(input))

}
