fun main() {
    data class SensorGroup(val sensor: Pair<Int, Int>, val beacon: Pair<Int, Int>, val distance: Int)

    fun String.parse() = "(-?\\d+)".toRegex().findAll(this).map { it.groupValues[1].toInt() }.toList().chunked(2)
        .map { Pair(it[0], it[1]) }.let { SensorGroup(it.first(), it.last(), it.first().manhattan(it.last())) }

    fun notInRow(sensorGroup: List<SensorGroup>, row: Int) = buildMap {
        for (g in sensorGroup) {
            val d = when {
                g.sensor.second > row && g.sensor.second - g.distance <= row -> row - (g.sensor.second - g.distance)
                g.sensor.second < row && g.sensor.second + g.distance >= row -> g.sensor.second + g.distance - row
                else -> continue
            }
            for (i in g.sensor.first - d..g.sensor.first + d) {
                this[i] = true
            }
        }
    }.size - sensorGroup.asSequence().map { listOf(it.sensor, it.beacon) }.flatten().filter { it.second == row }.toSet()
        .count()

    fun part1(input: List<String>, row: Int) = notInRow(input.map { it.parse() }, row)

    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    check(part1(testInput, 10) == 26)
    println(part1(testInput, 10))
    println(part1(input, 2000000))
}
