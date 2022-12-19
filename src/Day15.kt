import kotlin.math.max
import kotlin.math.min

fun main() {
    data class SensorGroup(val sensor: Pair<Int, Int>, val beacon: Pair<Int, Int>, val distance: Int)

    fun String.parse() = "(-?\\d+)".toRegex().findAll(this).map { it.groupValues[1].toInt() }.toList().chunked(2)
        .map { Pair(it[0], it[1]) }.let { SensorGroup(it.first(), it.last(), it.first().manhattan(it.last())) }

    fun MutableList<Pair<Int, Int>>.minus(other: Pair<Int,Int>): MutableList<Pair<Int, Int>> {
        val values = mutableListOf<Pair<Int, Int>>()
        for(p in this) {
            if(other.first <= p.first && other.second >= p.second) continue
            else if(other.first >= p.first && other.first <= p.second && other.second >= p.second) values.add(p.first to other.first - 1)
            else if(other.first <= p.first && other.second >= p.first) values.add(other.second + 1  to p.second)
            else if(other.first >= p.first && other.second <= p.second) {
                values.add(p.first to other.first - 1)
                values.add(other.second + 1 to p.second)
            }
            else values.add(p)
        }
        return values
    }

    fun inRow(sensorGroup: List<SensorGroup>, row: Int, limits: Pair<Int, Int>? = null) = buildMap {
        for (g in sensorGroup) {
            val d = when {
                g.sensor.second >= row && g.sensor.second - g.distance <= row -> row - (g.sensor.second - g.distance)
                g.sensor.second <= row && g.sensor.second + g.distance >= row -> g.sensor.second + g.distance - row
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

    fun notInRow(sensorGroup: List<SensorGroup>, row: Int, limits: Pair<Int, Int>): MutableList<Pair<Int, Int>> {
        var line = mutableListOf(limits)
        for (g in sensorGroup) {
            val d = when {
                g.sensor.second >= row && g.sensor.second - g.distance <= row -> row - (g.sensor.second - g.distance)
                g.sensor.second <= row && g.sensor.second + g.distance >= row -> g.sensor.second + g.distance - row
                else -> continue
            }

            if (g.sensor.first - d >= limits.first && g.sensor.first - d <= limits.second || g.sensor.first + d >= limits.first && g.sensor.first + d <= limits.second) {
                val range = max(limits.first, g.sensor.first - d) to min(limits.second, g.sensor.first + d)
                line = line.minus(range)
            }
        }
        return line
    }


    fun part1(input: List<String>, row: Int): Int {
        val sensors = input.map { it.parse() }
        return inRow(sensors, row).size - sensors.asSequence().map { listOf(it.sensor, it.beacon) }.flatten()
            .filter { it.second == row }.toSet()
            .count()
    }

    fun part2(input: List<String>, limits: Pair<Int, Int>): Long {
        val sensors = input.map { it.parse() }
        for (i in limits.first..limits.second) {
            val row = notInRow(sensors, i, limits)
            if (notInRow(sensors, i, limits).isNotEmpty())
                return row.first().first.toLong() * 4000000 + i
        }
        return 0L
    }


    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    check(part1(testInput, 10) == 26)
    println(part1(testInput, 10))
    println(part1(input, 2000000))

    println(part2(testInput, 0 to 20))
    println(part2(input, 0 to 4000000))


}
