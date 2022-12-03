fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for(line in input) {
            val (firstFalf, secondHalf) =  line.take(line.length/2) to line.substring(line.length/2)
            val value = firstFalf.toSet().intersect(secondHalf.toSet()).first().getValue()
            sum += value
        }
        return sum
    }


    fun part2(input: List<String>) : Int {
        val chunkedList = input.chunked(3)
        var sum = 0
        for (chunk in chunkedList) {
            val value = chunk[0].toSet().intersect(chunk[1].toSet().intersect(chunk[2].toSet())).first().getValue()
            sum += value
        }

        return sum
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
