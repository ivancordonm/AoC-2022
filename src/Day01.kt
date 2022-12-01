fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        var partial = 0
        for (weight in input) {
            if(weight.isNotBlank()) {
                partial += weight.toInt()
            } else {
                if (partial > max) max = partial
                partial = 0
            }
        }
        return max
    }

    fun part2(input: List<String>): Int {
        var partial = 0
        val weights = ArrayList<Int>()
        for (weight in input) {
            if(weight.isNotBlank() ) {
                partial += weight.toInt()
            } else {
                weights.add(partial)
                partial = 0
            }
        }
        weights.add(partial)
        val sortedList = weights.sortedDescending()
        return sortedList[0] + sortedList[1] + sortedList[2]
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    val soltest1 = part1(testInput)
    check(soltest1 == 24000)
    println(part1(input))

    val soltest2 = part2(testInput)
    check(soltest2 == 45000)
    println(part2(input))


}
