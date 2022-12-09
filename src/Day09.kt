import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {

        var h = Pair(0, 0)
        var t = Pair(0, 0)
        val visited = mutableSetOf(t)

        for (line in input) {
            val (direction, steps) = line.split(" ")
            for (step in 1..steps.toInt()) {
                val prev = h
                when (direction) {
                    "U" -> h = h.first + 1 to h.second
                    "D" -> h = h.first - 1 to h.second
                    "R" -> h = h.first to h.second + 1
                    "L" -> h = h.first to h.second - 1
                }
                if (!t.isTouching(h)) {
                    t = prev
                    visited.add(t)
                }
            }
        }

        return visited.size
    }

    fun printBoard(knots: MutableList<Pair<Int, Int>>) {
        for (row in (0..10).reversed()) {
            for (col in 0..10) {
                if (Pair(row, col) in knots) print("${knots.indexOf(Pair(row, col))} ")
                else print(". ")
            }
            println()
        }
        println()
    }

    fun part2(input: List<String>): Int {

        val knots = MutableList(10) { 0 to 0 }
        val visited = mutableSetOf(0 to 0)

        for (line in input) {
            printBoard(knots)
            val (direction, steps) = line.split(" ")
            for (step in 1..steps.toInt()) {
                val prev = knots.toMutableList()
                when (direction) {
                    "U" -> knots[0] = knots[0].first + 1 to knots[0].second
                    "D" -> knots[0] = knots[0].first - 1 to knots[0].second
                    "R" -> knots[0] = knots[0].first to knots[0].second + 1
                    "L" -> knots[0] = knots[0].first to knots[0].second - 1
                }

                if (!knots[1].isTouching(knots.first())) {
                    for (index in knots.indices.drop(1)) {
                        if (!knots[index].isTouching(knots[index - 1])) {
                            if (knots[index].isDiagonal(knots[index - 1])) {
                                val tmp = listOf(
                                    1 to 1,
                                    1 to -1,
                                    -1 to 1,
                                    -1 to -1
                                ).map { it.first + knots[index].first to it.second + knots[index].second }
                                    .first { p -> p.isTouching(knots[index - 1]) }
                                knots[index] = tmp
                            } else knots[index] = prev[index - 1]
                        }
                    }
                }
                visited.add(knots.last())
            }
        }

        return visited.size
    }

    val testInput = readInput("Day09_test")
    val testInput2 = readInput("Day09_test2")
    val input = readInput("Day09")

//    part1(testInput)
//    println(part1(testInput))
//    check(part1(testInput) == 13)
//    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1)
//    println(part2(input))
}

private fun Pair<Int, Int>.isTouching(h: Pair<Int, Int>) =
    (h.first - first).absoluteValue <= 1 && (h.second - second).absoluteValue <= 1

private fun Pair<Int, Int>.isDiagonal(h: Pair<Int, Int>) =
    h.first != first && h.second != second
