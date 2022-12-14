import kotlin.math.max
import kotlin.math.min

fun main() {

    var left = 0
    var maxDepth = 0

    fun Array<CharArray>.print() {
        for (row in this) {
            println(row.contentToString().filterNot { it == ',' })
        }
    }

    fun String.parse() =
        this.split(" -> ")
            .map { it.split(",") }
            .map { elem -> Pair(elem.first().toInt(), elem.last().toInt()) }
            .zipWithNext()

    fun initializeMap(rocks: List<List<Pair<Pair<Int, Int>, Pair<Int, Int>>>>): Array<CharArray> {

        maxDepth =
            rocks.flatMap { it.map { elem -> listOf(elem.first.second, elem.second.second) } }.flatten().max()
        val (leftLocal, right) = rocks.flatMap { it.map { elem -> listOf(elem.first.first, elem.second.first) } }
            .flatten()
            .let { pos ->
                listOf(pos.min(), pos.max())
            }

        left = leftLocal

        val caveMap = Array(maxDepth + 1) { CharArray(right - left + 1) { '.' } }
        for (rock in rocks) {
//            println(rock)
            rock.forEach { r ->
                val (minRow, maxRow) = listOf(
                    min(r.first.second, r.second.second),
                    max(r.first.second, r.second.second)
                )
                val (minCol, maxCol) = listOf(
                    min(r.first.first - left, r.second.first - left),
                    max(r.first.first - left, r.second.first - left)
                )
                for (i in minRow..maxRow)
                    for (j in minCol..maxCol) {
                        caveMap[i][j] = '#'
                    }
                caveMap[0][500 - left] = '+'

            }
        }
        caveMap.print()

        return caveMap
    }

    fun runGame(cave: Array<CharArray>): Int {
//        val caveT = Array(cave.first().size) { index -> cave.map { it[index] }.toCharArray() }
        var count = 0
        val sandCol = 500 - left
        var pos = 0 to sandCol
        while (true) {
            count++
            //var pos = caveT[sandCol].indexOfFirst { "[#o]".toRegex().matches(it.toString()) }
            var pos = 0 to sandCol
            for(i in 1..maxDepth) {
                if (pos.first == maxDepth) return count
                if("[#o]".toRegex().matches(cave[i][sandCol].toString())) {

                }
            }
        }
    }


    fun part1(input: List<String>): Int {
        val rocks = input.map { it.parse() }
        val cave = initializeMap(rocks)

        return runGame(cave)

    }


    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

//    check(part1(testInput) == 24)
//    println(part1(testInput))
    println(part1(input))
//
//    println(part2(testInput))
//    check(part2(testInput) == 140)
//    println(part2(input))

}
