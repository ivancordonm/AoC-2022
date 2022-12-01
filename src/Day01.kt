fun main() {

    fun part1(input: List<String>) = input.sumarize().first()

    fun part2(input: List<String>) = input.sumarize().take(3).sum()

    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    check(part1(testInput) == 24000)
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))

}

private fun List<String>.sumarize() =
    listOf(
        -1,
        *mapIndexedNotNull { index, item -> index.takeIf { item.isBlank() } }.toTypedArray(),
        lastIndex + 1
    ).zipWithNext().let { indexes ->
        indexes.map { pair ->
            this.subList(pair.first + 1, pair.second).sumOf { it.toInt() }
        }
    }.sortedDescending()
