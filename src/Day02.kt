fun main() {

    val scoreMap = mapOf(
        Pair("A", "X") to 1 + 3,
        Pair("A", "Y") to 2 + 6,
        Pair("A", "Z") to 3 + 0,
        Pair("B", "X") to 1 + 0,
        Pair("B", "Y") to 2 + 3,
        Pair("B", "Z") to 3 + 6,
        Pair("C", "X") to 1 + 6,
        Pair("C", "Y") to 2 + 0,
        Pair("C", "Z") to 3 + 3,

        )

    val chooseMap = mapOf(
        Pair("A", "X") to Pair("A", "Z"),
        Pair("A", "Y") to Pair("A", "X"),
        Pair("A", "Z") to Pair("A", "Y"),
        Pair("B", "X") to Pair("B", "X"),
        Pair("B", "Y") to Pair("B", "Y"),
        Pair("B", "Z") to Pair("B", "Z"),
        Pair("C", "X") to Pair("C", "Y"),
        Pair("C", "Y") to Pair("C", "Z"),
        Pair("C", "Z") to Pair("C", "X"),
    )

    /*
        A, X -> Rock  (1)
        B, Y -> Paper  (2)
        C, Z -> Scissors  (3)
        Rock defeats Scissors, Scissors defeats Paper, Paper defeats Rock
        0 loose, 3 draw, 6 win
     */
    fun part1(input: List<String>) =
        input.mapNotNull { scoreMap[Pair(it.split(" ").first(), it.split(" ").last())] }.sum()

    /*
        X -> lose
        Y -> draw
        Z -> win
     */
    fun part2(input: List<String>) =
        input.mapNotNull { scoreMap[chooseMap[Pair(it.split(" ").first(), it.split(" ").last())]] }.sum()


    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    println(part1(testInput))
    check(part1(testInput) == 15)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 12)
    println(part2(input))


}
