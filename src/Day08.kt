fun main() {


    fun part1(input: List<String>): Int {

        var total = 0
        val top = 0
        val left = 0
        val down = input.size - 1
        val right = input.first().length

        for ((i, row) in input.withIndex())
            for ((j, elem) in row.withIndex()) {
                if (i == top || i == down || j == left || j == right) {
                    total++
                    continue
                }
                if (input[i - 1][j] <= elem || input[i][j - 1] <= elem || input[i + 1][j] <= elem || input[i][j + 1] <= elem) total++
            }

        return total
    }


    fun part2(input: List<String>): Int {

        TODO()
    }

    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    println(part1(testInput))
    check(part1(testInput) == 21)
    println(part1(input))

//    println(part2(testInput))
//    check(part2(testInput) == 0)
//    println(part2(input))
}
