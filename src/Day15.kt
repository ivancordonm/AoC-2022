fun main() {

    val excess = 20
    var minY = 0

    fun Array<CharArray>.print() {
        for (row in this) {
            println(row.contentToString().filterNot { it == ',' })
        }
    }

    fun String.parse() = "(-?\\d+)".toRegex().findAll(this).map { it.groupValues[1].toInt() }.toList().chunked(2)
        .map { Pair(it[0], it[1]) }


    fun fillMap(sensors: List<List<Pair<Int, Int>>>): Array<CharArray> {

        val xRange = sensors.map { listOf(it.first().first, it.last().first) }.flatten().let { list ->
            listOf(list.min() - excess, list.max() + excess)
        }
        val yRange =
            sensors.map { listOf(it.first().second, it.last().second) }.flatten().let { list ->
                listOf(list.min() - excess, list.max() + excess)
            }

        minY = yRange.first()

        val caveMap =
            Array(yRange.last() - yRange.first() + 1) { CharArray(xRange.last() - xRange.first() + 1) { '.' } }

        for (sensor in sensors) {
            val s = sensor.first() + (-xRange.first() to -yRange.first())
            val b = sensor.last() + (-xRange.first() to -yRange.first())
            caveMap[s.second][s.first] = 'S'
            caveMap[b.second][b.first] = 'B'
        }

//        caveMap.print()

        for (sensor in sensors) {
            val s = sensor.first() + (-xRange.first() to -yRange.first())
            val b = sensor.last() + (-xRange.first() to -yRange.first())
            val distance = s.manhattan(b)
            for (i in -distance..distance) {
                for (j in -distance..distance) {
                    val plus = s + (i to j)
                    if (s.manhattan(plus) <= distance) {
                        if (caveMap[plus.second][plus.first] == '.')
                            caveMap[plus.second][plus.first] = '#'
                    }
                }
            }
        }
//        caveMap.print()

        return caveMap

    }


    fun part1(input: List<String>): Int {
        val sensors = input.map { it.parse() }
        val cave = fillMap(sensors)
        return cave[2000000 - minY].count { it == '#' }
    }


    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

//    check(part1(testInput) == 26)
//    println(part1(testInput))
    println(part1(input))


}
