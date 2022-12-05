fun main() {
    data class StacksInfo(
        val lineStacks: Int,
        val positions: List<Int>,
        val stacks: MutableList<MutableList<Char>>
    )

    fun parseInput(input: List<String>): StacksInfo {

        val tempInfo = input.mapIndexed { index, value -> index to value }
            .first { (_, it) -> it.matches(" \\d+(\\s+\\d+)*".toRegex()) }

        val stacksInfo = StacksInfo(
            tempInfo.first,
            tempInfo.second.mapIndexed { index, c -> if (c.isDigit()) index else null }.filterNotNull(),
            MutableList(tempInfo.second.last().digitToInt()) { mutableListOf() }
        )

        input.take(stacksInfo.lineStacks).reversed().map { line ->
            stacksInfo.positions.mapIndexed { i, p ->
                if (p < line.length && line[p].isLetter()) stacksInfo.stacks[i].add(
                    line[p]
                )
            }
        }
        return stacksInfo
    }

    fun part1(input: List<String>): String {

        val stacksInfo = parseInput(input)

        for (line in input.drop(stacksInfo.lineStacks + 2)) {
            val (num, from, to) = "(\\d+)".toRegex().findAll(line).map { it.value.toInt() }.toList()
            stacksInfo.stacks[to - 1].addAll(stacksInfo.stacks[from - 1].takeLast(num).reversed())
            stacksInfo.stacks[from - 1] = stacksInfo.stacks[from - 1].dropLast(num).toMutableList()
        }

        return buildString {
            for (s in stacksInfo.stacks) append(s.last())
        }
    }


    fun part2(input: List<String>): String {
        val stacksInfo = parseInput(input)

        for (line in input.drop(stacksInfo.lineStacks + 2)) {
            val (num, from, to) = "(\\d+)".toRegex().findAll(line).map { it.value.toInt() }.toList()
            stacksInfo.stacks[to - 1].addAll(stacksInfo.stacks[from - 1].takeLast(num))
            stacksInfo.stacks[from - 1] = stacksInfo.stacks[from - 1].dropLast(num).toMutableList()
        }

        return buildString {
            for (s in stacksInfo.stacks) append(s.last())
        }
    }


    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    println(part1(testInput))
    check(part1(testInput) == "CMZ")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == "MCD")
    println(part2(input))
}
