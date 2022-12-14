import java.util.*

fun main() {

    fun String.parse(): Item.Values {
        val stack = Stack<Item.Values>()
        var s = this
        val root = Item.Values(mutableListOf())
        var current = root
        var num = ""
        while (s.isNotEmpty()) {
            when (s.first()) {
                '[' -> {
                    val inner = Item.Values(mutableListOf())
                    current.values.add(inner)
                    stack.push(current)
                    current = inner
                }

                ']' -> {
                    if (num.isNotEmpty()) {
                        current.values.add(Item.Value(num.toInt()))
                        num = ""
                    }
                    current = stack.pop()
                }

                ',' -> {
                    if (num.isNotEmpty()) {
                        current.values.add(Item.Value(num.toInt()))
                        num = ""
                    }
                }

                else -> {
                    num += s.first()
                }
            }
            s = s.drop(1)
        }
        return root
    }


    fun part1(input: List<String>) = input
        .chunked(3)
        .mapIndexed { index, lines -> Pair(index + 1, lines[0].parse() < lines[1].parse()) }
        .filter { it.second }
        .sumOf { it.first }


    fun part2(input: List<String>): Int {

        val sortedList =input.filter { it.isNotEmpty() }.map { it.parse() }.sorted().toMutableList()
        val positions = mutableListOf<Int>()
        for (elem in listOf("[[2]]".parse(), "[[6]]".parse())) {
            for((idx, l) in sortedList.withIndex())
                if(elem < l)  {
                    positions.add(idx+1)
                    sortedList.add(idx+1, elem)
                    break
                }
        }
        return positions.reduce { acc, i ->  acc * i}
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    check(part1(testInput) == 13)
    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 140)
    println(part2(input))

}
