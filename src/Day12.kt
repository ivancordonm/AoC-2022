import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.set

fun main() {


    fun bfs(input: List<String>, start: Pair<Int, Int>): MutableMap<Pair<Int, Int>, Pair<Int, Int>> {

        val visited = mutableMapOf<Pair<Int, Int>, Boolean>()
        val edgeTo = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>()

        fun adjacents(c: Pair<Int, Int>): List<Pair<Int, Int>> {
            return buildList {
                listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1).forEach { p ->
                    val next = c.first + p.first to c.second + p.second
                    if (next.first >= 0 &&
                        next.first <= input.lastIndex &&
                        next.second >= 0 &&
                        next.second <= input.first().lastIndex &&
                        input.element(next).value() <= input.element(c).value() + 1 &&
                        !visited.contains(next)

                    )
                        add(next)
                }
            }
        }

        visited[start] = true
        queue.add(start)

        while (queue.isNotEmpty()) {
            val vertex = queue.removeFirst()
            for (adjacent in adjacents(vertex)) {
                if (!visited.contains(adjacent)) {
                    edgeTo[adjacent] = vertex
                    visited[adjacent] = true
                    queue.add(adjacent)

                }
            }
        }

        return edgeTo

    }

    fun MutableMap<Pair<Int, Int>, Pair<Int, Int>>.pathTo(
        source: Pair<Int, Int>,
        destination: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        val stack = Stack<Pair<Int, Int>>()
        var x = destination
        while (x != source) {
            stack.push(x)
            x = this[x]!!
        }
        return stack.toList()
    }

    fun part1(input: List<String>) =
        bfs(input, input.position('S')).pathTo(input.position('S'), input.position('E')).count()


    fun part2(input: List<String>) =
        input.allPositions('a').minOf { bfs(input, it).pathTo(it, input.position('E')).count() }


    val testInput = readInput("Day12_test")
    val input = readInput("Day12")


    check(part1(testInput) == 31)
    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 29)
    println(part2(input))

}

private fun List<String>.element(pos: Pair<Int, Int>) = this[pos.first][pos.second]

private fun List<String>.position(c: Char) =
    mapIndexed { index, s -> index to s }.filter { it.second.contains(c) }
        .map { elem -> elem.first to elem.second.indexOf(c) }.first()

private fun List<String>.allPositions(c: Char) =
    mapIndexed { index, s -> index to s }.filter { it.second.contains(c) }
        .map { elem -> elem.first to elem.second.indexOf(c) }

private fun Char.value() =
    when (this) {
        'S' -> 'a' - 1
        'E' -> 'z' + 1
        else -> this
    }
