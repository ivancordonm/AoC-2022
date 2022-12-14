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

        val caveMap = Array(maxDepth + 1) { CharArray(right - left + 3) { '.' } }
        for (rock in rocks) {
//            println(rock)
            rock.forEach { r ->
                val (minRow, maxRow) = listOf(
                    min(r.first.second, r.second.second),
                    max(r.first.second, r.second.second)
                )
                val (minCol, maxCol) = listOf(
                    min(r.first.first - left, r.second.first - left) + 1,
                    max(r.first.first - left, r.second.first - left) + 1
                )
                for (i in minRow..maxRow)
                    for (j in minCol..maxCol) {
                        caveMap[i][j] = '#'
                    }
                caveMap[0][500 - left] = '+'
//                caveMap.print()

            }
        }
//        caveMap.print()

        return caveMap
    }

    fun initializeMapSecondPart(rocks: List<List<Pair<Pair<Int, Int>, Pair<Int, Int>>>>): Array<CharArray> {

        maxDepth =
            rocks.flatMap { it.map { elem -> listOf(elem.first.second, elem.second.second) } }.flatten().max() + 2
        val (leftLocal, right) = rocks.flatMap { it.map { elem -> listOf(elem.first.first, elem.second.first) } }
            .flatten()
            .let { pos ->
                listOf(pos.min(), pos.max())
            }

        left = leftLocal

        val caveMap = Array(maxDepth + 1) { CharArray(right - left + 800) { '.' } }
        for (rock in rocks) {
//            println(rock)
            rock.forEach { r ->
                val (minRow, maxRow) = listOf(
                    min(r.first.second, r.second.second),
                    max(r.first.second, r.second.second)
                )
                val (minCol, maxCol) = listOf(
                    min(r.first.first - left, r.second.first - left) + 400,
                    max(r.first.first - left, r.second.first - left) + 400
                )
                for (i in minRow..maxRow)
                    for (j in minCol..maxCol) {
                        caveMap[i][j] = '#'
                    }
                caveMap[0][500 - left + 400] = '+'
//                caveMap.print()
            }
        }
        caveMap[maxDepth] = CharArray(right - left + 800) {'#'}
        caveMap.print()

        return caveMap
    }


    fun runGameUntilflowing(cave: Array<CharArray>): Int {

        fun Pair<Int, Int>.leftDown() = cave[first + 1][second - 1]
        fun Pair<Int, Int>.down() = cave[first + 1][second]
        fun Pair<Int, Int>.rightDown() = cave[first + 1][second + 1]

        fun Pair<Int, Int>.lock() = leftDown() != '.' && down() != '.' && rightDown() != '.'

        var count = 0
        val sandCol = 500 - left + 1
        while (true) {
            var pos = 0 to sandCol

            while (pos.first != maxDepth && !pos.lock()) {
                if (pos.down() == '.') pos = pos.first + 1 to pos.second
                else if (pos.leftDown() == '.') pos = pos.first + 1 to pos.second - 1
                else if (pos.rightDown() == '.') pos = pos.first + 1 to pos.second + 1
            }
            if (pos.first == maxDepth) break
            cave[pos.first][pos.second] = 'o'
            count++
//            cave.print()
        }
//        println(count)
//        cave.print()
        return count
    }

    fun runGameUntilFilled(cave: Array<CharArray>): Int {

        fun Pair<Int, Int>.leftDown() = cave[first + 1][second - 1]
        fun Pair<Int, Int>.down() = cave[first + 1][second]
        fun Pair<Int, Int>.rightDown() = cave[first + 1][second + 1]

        fun Pair<Int, Int>.lock() = leftDown() != '.' && down() != '.' && rightDown() != '.'

        var count = 1
        val sandCol = 500 - left + 400
        while (true) {
            var pos = 0 to sandCol
            if(pos.lock()) break
            while (!pos.lock()) {
                if (pos.down() == '.') pos = pos.first + 1 to pos.second
                else if (pos.leftDown() == '.') pos = pos.first + 1 to pos.second - 1
                else if (pos.rightDown() == '.') pos = pos.first + 1 to pos.second + 1
            }
            cave[pos.first][pos.second] = 'o'
            count++
//            cave.print()
//            println()
        }
//        println(count)
//        cave.print()
        return count
    }

    //var pos = caveT[sandCol].indexOfFirst { "[#o]".toRegex().matches(it.toString()) }

    fun part1(input: List<String>): Int {
        val rocks = input.map { it.parse() }
        val cave = initializeMap(rocks)

        return runGameUntilflowing(cave)

    }


    fun part2(input: List<String>): Int {
        val rocks = input.map { it.parse() }
        val cave = initializeMapSecondPart(rocks)

        return runGameUntilFilled(cave)
    }

    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

//    check(part1(testInput) == 24)
//    println(part1(testInput))
//    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 93)
    println(part2(input))

}
