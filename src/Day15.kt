import kotlin.math.max
import kotlin.math.min

fun main() {
    data class SensorGroup(val sensor: Pair<Int, Int>, val beacon: Pair<Int, Int>, val distance: Int)

    fun String.parse() = "(-?\\d+)".toRegex().findAll(this).map { it.groupValues[1].toInt() }.toList().chunked(2)
        .map { Pair(it[0], it[1]) }.let { SensorGroup(it.first(), it.last(), it.first().manhattan(it.last())) }

    fun notInRow(sensorGroup: List<SensorGroup>, row: Int, limits: Pair<Int, Int>? = null) = buildMap {
        for (g in sensorGroup) {
            val d = when {
                g.sensor.second > row && g.sensor.second - g.distance <= row -> row - (g.sensor.second - g.distance)
                g.sensor.second < row && g.sensor.second + g.distance >= row -> g.sensor.second + g.distance - row
                else -> continue
            }
            val range = if (limits != null) max(limits.first, g.sensor.first - d) to min(
                limits.second,
                g.sensor.first + d
            ) else g.sensor.first - d to g.sensor.first + d

            for (i in range.first..range.second) {
                this[i] = true
            }
        }
    }

    fun part1(input: List<String>, row: Int): Int {
        val sensors = input.map { it.parse() }
        return notInRow(sensors, row).size - sensors.asSequence().map { listOf(it.sensor, it.beacon) }.flatten()
            .filter { it.second == row }.toSet()
            .count()
    }

    fun part2(input: List<String>, row: Int, limits: Pair<Int, Int>): Int {
        val sensors = input.map { it.parse() }
        for (i in limits.first..limits.second) {
            val a = notInRow(sensors, i, limits).map { it.key }.sorted()
            val dif = notInRow(sensors, i, limits).map { it.key }.sorted().sum() - ((limits.second *  (limits.second + 1)) / 2)
            if (dif != 0) return dif * 40000 + i
        }
        return 0
    }


    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    val a = notInRow(testInput.map { it.parse() }, 0).map { it.key }.sorted()
//    check(part1(testInput, 10) == 26)
//    println(part1(testInput, 10))
//    println(part1(input, 2000000))

    println(part2(testInput, 10, 0 to 20))



}
