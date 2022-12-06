fun main() {

    fun getPositionWithNDistinct(n: Int, sequence: String) = List(sequence.length) { index ->
        if (sequence.substring(index).take(n).toSet().size == n) 1 else 0
    }.indexOf(1) + n

    fun part1(input: List<String>) =
        getPositionWithNDistinct(4, input.first())


    fun part2(input: List<String>) =
        getPositionWithNDistinct(14, input.first())


    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    println(part1(testInput))
    check(part1(testInput) == 7)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 19)
    println(part2(input))
}
