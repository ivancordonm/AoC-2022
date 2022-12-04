fun main() {

    fun part1(input: List<String>) =
        input.map {
            it.split(",").map { pairs -> pairs.split("-") }.map { pair -> pair.first().toInt() to pair.last().toInt() }
                .let { pairs -> pairs.first().isFully(pairs.last()) }
        }.count { it }


    fun part2(input: List<String>) =
        input.map {
            it.split(",").map { pairs -> pairs.split("-") }.map { pair -> pair.first().toInt() to pair.last().toInt() }
                .let { pairs -> pairs.first().isPartial(pairs.last()) }
        }.count { it }


    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    println(part1(testInput))
    check(part1(testInput) == 2)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 4)
    println(part2(input))
}

private fun Pair<Int, Int>.isFully(other: Pair<Int, Int>) =
    (first <= other.first && second >= other.second) || (other.first <= first && other.second >= second)

private fun Pair<Int, Int>.isPartial(other: Pair<Int, Int>) = (first..second).toSet().intersect(
    (other.first..other.second).toSet()).isNotEmpty()
