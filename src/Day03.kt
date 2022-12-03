fun main() {

    fun part1(input: List<String>) =
        input.fold(0) { acc, it ->
            acc + it.take(it.length / 2).toSet().intersect(it.substring(it.length / 2).toSet()).first().getValue()
        }

    fun part2(input: List<String>) =
        input.chunked(3).fold(0) { acc, triple ->
            acc + triple[0].toSet().intersect(triple[1].toSet().intersect(triple[2].toSet())).first().getValue()
        }


    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    println(part1(testInput))
    check(part1(testInput) == 157)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 70)
    println(part2(input))


}

private fun Char.getValue() = if (isLowerCase()) code - 96 else code - 38
